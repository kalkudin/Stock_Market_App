package com.example.finalproject.presentation.screen.company_details

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.app.DatePickerDialog
import android.graphics.Color
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.FragmentCompanyDetailsBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.company_details.CompanyDetailsEvents
import com.example.finalproject.presentation.extension.loadImage
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.model.company_details.CompanyChartIntradayModel
import com.example.finalproject.presentation.state.company_details.CompanyDetailsState
import com.example.finalproject.presentation.util.DateValueFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CompanyDetailsFragment :
    BaseFragment<FragmentCompanyDetailsBinding>(FragmentCompanyDetailsBinding::inflate) {

    private val viewModel: CompanyDetailsViewModel by viewModels()
    private lateinit var lineChart: LineChart

    override fun bind() {
        super.bind()
        extractCompanySymbol()
        lineChart = binding.lineChart
        spinnerForIntradaySetup()
        configureChart()
    }

    override fun bindViewActionListeners() {
        handleBackButton()
        buttonSetup()
    }

    override fun bindObservers() {
        observeCompanyDetailsState()
    }

    private fun observeCompanyDetailsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.companyDetailsState.collect {
                    handleCompanyDetails(it)
                }
            }
        }
    }

    private fun handleCompanyDetails(state: CompanyDetailsState) {
        state.companyDetails?.let { companyDetailsList ->
            val firstCompanyDetails = companyDetailsList.first()
            binding.apply {
                civLogo.loadImage(firstCompanyDetails.image)
                tvSymbol.text = firstCompanyDetails.symbol
                tvName.text = firstCompanyDetails.companyName
                tvDesc.text = firstCompanyDetails.description
            }

        }
        state.companyChartIntraday?.let { chartData ->
            updateChart(chartData)
        }
        state.errorMessage?.let {
            binding.root.showSnackBar(it)
        }
        binding.progressBar.isVisible = state.isLoading
    }

    private fun handleBackButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun extractCompanySymbol() {
        val symbol = arguments?.getString("symbol") ?: ""
        viewModel.onEvent(CompanyDetailsEvents.GetCompanyDetails(symbol = symbol))
    }

    //

    private fun extractCompanyDetails(interval: String, fromDate: String, toDate: String) {
        val symbol = arguments?.getString("symbol") ?: ""
        viewModel.onEvent(CompanyDetailsEvents.GetCompanyDetails(symbol = symbol))
        viewModel.onEvent(
            CompanyDetailsEvents.GetCompanyChartIntraday(
                interval,
                symbol,
                fromDate,
                toDate
            )
        )
    }

    private fun spinnerForIntradaySetup() {
        val intervals = arrayOf("1min", "5min", "15min", "30min", "1hour", "4hour")
        val adapter = ArrayAdapter(requireContext(), simple_spinner_item, intervals)
        adapter.setDropDownViewResource(simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
    }

    private fun buttonSetup() {
        var fromDate = ""
        var toDate = ""

//        binding.btnFromDate.setOnClickListener {
//            showDatePickerDialog { date ->
//                fromDate = date
//            }
//        }
//
//        binding.btnToDate.setOnClickListener {
//            showDatePickerDialog { date ->
//                toDate = date
//            }
//
//        }
        binding.btnFromDate.setOnClickListener {
            showDatePickerDialog(binding.btnFromDate) { date ->
                fromDate = date
            }
        }

        binding.btnToDate.setOnClickListener {
            showDatePickerDialog(binding.btnToDate) { date ->
                toDate = date
            }
        }

        binding.btnFetch.setOnClickListener {
            val interval = binding.spinner.selectedItem.toString()
            extractCompanyDetails(interval, fromDate, toDate)
            Log.d("CompanyDetailsFragment", "buttonSetup: $interval, $fromDate, $toDate")
        }
    }

//    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
//            val selectedDate =
//                String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
//            onDateSelected(selectedDate)
//        }, year, month, day).show()
//    }
private fun showDatePickerDialog(button: Button, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
        val selectedDate =
            String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
        button.text = selectedDate
        onDateSelected(selectedDate)
    }, year, month, day).show()
}

    private fun configureChart() {
        lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            setBackgroundColor(Color.LTGRAY)

            // Customize the x-axis
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textColor = Color.BLACK
                textSize = 12f
                setDrawGridLines(false)
                valueFormatter = DateValueFormatter()
            }

            // Customize the left y-axis
            axisLeft.apply {
                textColor = Color.RED
                textSize = 12f
                setDrawGridLines(false)
            }

            // Disable the right y-axis
            axisRight.isEnabled = true

            // Customize the legend
            legend.isEnabled = false
        }
    }

    private fun updateChart(data: List<CompanyChartIntradayModel>) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timestamps = data.map { dateFormat.parse(it.date)?.time?.toFloat() ?: 0f }
        val minTimestamp = timestamps.minOrNull() ?: 0f

        val entries = data.map {
            val dateInMillis = dateFormat.parse(it.date)?.time?.toFloat() ?: 0f
            val normalizedTimestamp = dateInMillis - minTimestamp
            Log.d("updateChart", "date: ${it.date}, timestamp: $normalizedTimestamp")
            Entry(normalizedTimestamp, it.close)
        }.sortedBy { it.x }

        entries.forEach { entry ->
            Log.d("updateChart", "entry: ${entry.x}, ${entry.y}")
        }

        val dataSet = LineDataSet(entries, "Intraday Chart").apply {
            setDrawCircles(false)
            setDrawValues(false)
            color = Color.BLUE
            lineWidth = 2f
        }

        lineChart.data = LineData(dataSet)
        lineChart.invalidate()
    }
}
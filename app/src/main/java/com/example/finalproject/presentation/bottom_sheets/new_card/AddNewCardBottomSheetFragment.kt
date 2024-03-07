package com.example.finalproject.presentation.bottom_sheets.new_card

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.BottomSheetAddNewCardLayoutBinding
import com.example.finalproject.presentation.extension.setOnItemSelected
import com.example.finalproject.presentation.bottom_sheets.event.AddNewCardEvent
import com.example.finalproject.presentation.bottom_sheets.state.NewCardState
import com.example.finalproject.presentation.model.funds.CreditCard
import com.example.finalproject.presentation.util.formatExpirationDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNewCardBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddNewCardLayoutBinding? = null
    private val binding get() = _binding!!

    private val addNewCardViewModel : AddNewCardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetAddNewCardLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCardTypeAdapter()
        bindNumbersField()
        bindNewCreditCard()
        bindSuccessFlow()
        bindNavigationFlow()
    }

    private fun bindNewCreditCard() {
        with(binding) {
            btnAddCreditCard.setOnClickListener {
                addNewCardViewModel.onEvent(
                    AddNewCardEvent.AddCreditCard(
                    cardNumber = etCardNumber.text?.chunked(size = 4) ?: emptyList(),
                    expirationDate = formatExpirationDate(etExpiryMonth, etExpiryYear),
                    ccv = etCcv.text.toString(),
                    cardType = getCardType()))
            }
        }
    }

    private fun bindCardTypeAdapter() {
        val cardTypes = resources.getStringArray(R.array.card_type_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cardTypes).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerCardType.adapter = adapter

        binding.spinnerCardType.setOnItemSelected { cardType ->
            updateCardIcon(cardType)
        }
    }

    private fun bindSuccessFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addNewCardViewModel.successFlow.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state : NewCardState) {
        with(binding) {
            if(state.isLoading) progressBar.visibility = View.VISIBLE

            state.errorMessage?.let { message -> handleErrorMessage(errorMessage = message) }

            if(state.success) {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addNewCardViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is NewCardNavigationFlow.NavigateBack -> handleNavigation(card = event.creditCard)
                    }
                }
            }
        }
    }

    private fun handleNavigation(card : CreditCard) {
        val action = AddNewCardBottomSheetFragmentDirections.actionAddNewCardBottomSheetFragmentToUserFundsFragment(card)
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun updateCardIcon(cardType: String) {
        with(binding) {
            val iconResId: Int
            val backgroundColorResId: Int
            when(cardType) {
                "Visa" -> {
                    iconResId = R.drawable.ic_visa
                    backgroundColorResId = R.color.sky_blue
                }
                "MasterCard" -> {
                    iconResId = R.drawable.ic_master_card
                    backgroundColorResId = R.color.light_orange
                }
                else -> {
                    iconResId = R.drawable.ic_visa
                    backgroundColorResId = R.color.sky_blue
                }
            }
            ivCardType.setImageResource(iconResId)
            cardContainer.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), backgroundColorResId))
        }
    }

    private fun getCardType(): String {
        return when (binding.spinnerCardType.selectedItem.toString()) {
            getString(R.string.visa) -> "visa"
            getString(R.string.mastercard) -> "master_card"
            else -> "unkown"
        }
    }

    private fun bindNumbersField() {
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var backspacingFlag = false
            private var cursorPosition = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                cursorPosition = binding.etCardNumber.selectionStart
                backspacingFlag = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isFormatting) {
                    isFormatting = true
                    val inputLength = s!!.length
                    val formattedString = s.toString().replace(" ", "").chunked(4).joinToString(" ")
                    s.replace(0, s.length, formattedString)

                    if (backspacingFlag && cursorPosition != inputLength) {
                        if (cursorPosition > formattedString.length) cursorPosition = formattedString.length
                        binding.etCardNumber.setSelection(cursorPosition)
                    } else if (cursorPosition < formattedString.length) {
                        binding.etCardNumber.setSelection(cursorPosition + 1)
                    }
                    isFormatting = false
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
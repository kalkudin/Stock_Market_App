package com.example.finalproject.presentation.screen.company_details

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentCompanyDetailsBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.adapters.company_details.CompanyDetailsRecyclerAdapter
import com.example.finalproject.presentation.event.company_details.CompanyDetailsEvents
import com.example.finalproject.presentation.state.company_details.CompanyDetailsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyDetailsFragment : BaseFragment<FragmentCompanyDetailsBinding>(FragmentCompanyDetailsBinding::inflate) {

    private val viewModel: CompanyDetailsViewModel by viewModels()
    private lateinit var companyDetailsAdapter : CompanyDetailsRecyclerAdapter

    override fun bind() {
        extractCompanySymbol()
    }

    override fun bindViewActionListeners() {
        //move this in setup later
        companyDetailsAdapterSetUp()
        handleBackButton()
    }

    override fun bindObservers() {
        observeCompanyDetailsState()
        observeCompanyDetailsNavigationEvents()
    }

    private fun companyDetailsAdapterSetUp() {
        companyDetailsAdapter = CompanyDetailsRecyclerAdapter()
        binding.rvDetails.apply {
            adapter = companyDetailsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
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

    private fun observeCompanyDetailsNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { event ->
                    handleNavigation(event)
                }
            }
        }
    }

    private fun handleCompanyDetails(state: CompanyDetailsState) {
        state.companyDetails?.let {companyDetailsAdapter.submitList(it)}
        state.errorMessage?.let {
            binding.root.showSnackBar(it)
        }
        binding.progressBar.isVisible = state.isLoading
    }

    private fun handleNavigation(event: CompanyDetailsViewModel.CompanyDetailsNavigationEvents) {
        when (event) {
            is CompanyDetailsViewModel.CompanyDetailsNavigationEvents.NavigateToCompanyList -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun handleBackButton() {
        binding.btnBack.setOnClickListener {
            viewModel.onEvent(CompanyDetailsEvents.BackButtonPressed)
        }
    }

    private fun extractCompanySymbol() {
        val symbol = arguments?.getString("symbol") ?: ""
        viewModel.onEvent(CompanyDetailsEvents.GetCompanyDetails(symbol = symbol))
    }
}
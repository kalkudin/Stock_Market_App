package com.example.finalproject.presentation.stock_feature.company_list.screen.company_list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.finalproject.databinding.FragmentCompanyListBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.stock_feature.company_list.adapters.CompanyListRecyclerAdapter
import com.example.finalproject.presentation.stock_feature.company_list.event.CompanyListEvents
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel
import com.example.finalproject.presentation.stock_feature.company_list.state.CompanyListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListFragment :
    BaseFragment<FragmentCompanyListBinding>(FragmentCompanyListBinding::inflate) {

    private val viewModel: CompanyListViewModel by viewModels()

    private lateinit var companyListAdapter: CompanyListRecyclerAdapter

    override fun bindViewActionListeners() {
        //later change the base fragment to initialize adapters
        companyListAdapterSetUp()
    }

    override fun bindObservers() {
        observeCompanyListState()
        observeCompanyListNavigationEvents()
    }

    private fun companyListAdapterSetUp() {
        companyListAdapter = CompanyListRecyclerAdapter(
            onCompanyClick = {
                handleCompanyClick(it)
            }
        )
        binding.rvList.apply {
            adapter = companyListAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }
        viewModel.onEvent(CompanyListEvents.GetCompanyList)
    }

    private fun observeCompanyListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.companyListState.collect {
                    handleCompanyListState(it)
                }
            }
        }
    }

    private fun observeCompanyListNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.companyListNavigationEvent.collect {
                    handleCompanyListNavigationEvents(it)
                }
            }
        }
    }

    private fun handleCompanyListState(state: CompanyListState) {
        state.companyList?.let { companyListAdapter.submitList(it) }
        state.errorMessage?.let { binding.root.showSnackBar(it) }
        binding.progressBar.isVisible = state.isLoading
    }

    private fun handleCompanyListNavigationEvents(event: CompanyListViewModel.CompanyListNavigationEvents) {
        when (event) {
            is CompanyListViewModel.CompanyListNavigationEvents.NavigateToCompanyDetails -> findNavController().navigate(
                CompanyListFragmentDirections.actionCompanyListFragmentToCompanyDetailsFragment(
                    event.symbol
                )
            )
        }
    }

    private fun handleCompanyClick(company: CompanyListModel) {
        viewModel.onEvent(CompanyListEvents.CompanyItemClick(company = company))
    }
}
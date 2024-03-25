package com.example.finalproject.presentation.screen.owned_stocks

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentOwnedStocksBinding
import com.example.finalproject.presentation.adapters.owned_stocks.OwnedStocksRecyclerAdapter
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.owned_stocks.OwnedStocksEvent
import com.example.finalproject.presentation.model.owned_stocks.OwnedStock
import com.example.finalproject.presentation.state.owned_stocks.OwnedStocksState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OwnedStocksFragment :
    BaseFragment<FragmentOwnedStocksBinding>(FragmentOwnedStocksBinding::inflate) {

    private val viewModel: OwnedStocksViewModel by viewModels()
    private lateinit var ownedStocksAdapter: OwnedStocksRecyclerAdapter

    override fun bind() {
        super.bind()
        ownedStocksAdapterSetup()
    }

    override fun bindViewActionListeners() {
        searchListener()
    }

    override fun bindObservers() {
        observeOwnedStocksNavigationEvents()
        observeOwnedStocks()
    }

    private fun searchListener() {
        binding.etSearch.addTextChangedListener {
            handleSearch(it.toString())
        }
    }

    private fun ownedStocksAdapterSetup() {
        ownedStocksAdapter = OwnedStocksRecyclerAdapter(
            onCompanyClick = {
                handleCompanyClick(it)
            }
        )
        binding.rvOwned.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ownedStocksAdapter
        }
        viewModel.onEvent(OwnedStocksEvent.GetOwnedStocks)
    }

    private fun observeOwnedStocks() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ownedStocksState.collect { state ->
                    handleOwnedStocksState(state)
                }
            }
        }
    }

    private fun handleOwnedStocksState(state: OwnedStocksState) {
        state.ownedStocks.let {
            ownedStocksAdapter.submitList(it)
        }
    }

    private fun observeOwnedStocksNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ownedNavigationEvent.collect {
                    handleOwnedNavigationEvents(it)
                }
            }
        }
    }

    private fun handleOwnedNavigationEvents(event: OwnedStocksViewModel.OwnedStocksNavigationEvents) {
        when (event) {
            is OwnedStocksViewModel.OwnedStocksNavigationEvents.NavigateToCompanyDetails -> findNavController().navigate(
                OwnedStocksFragmentDirections.actionOwnedStocksFragmentToCompanyDetailsFragment(
                    event.symbol
                )
            )
        }
    }

    private fun handleSearch(query: String) {
        viewModel.onEvent(OwnedStocksEvent.SearchOwnedStocks(query = query))
    }

    private fun handleCompanyClick(stock: OwnedStock) {
        viewModel.onEvent(OwnedStocksEvent.StocksItemClick(stock = stock))
    }
}
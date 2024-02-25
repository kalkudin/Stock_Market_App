package com.example.finalproject.presentation.stock_feature.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentStockHomeLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.home.adapter.StockHostAdapter
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StockHomeFragment : BaseFragment<FragmentStockHomeLayoutBinding>(FragmentStockHomeLayoutBinding::inflate){

    private val stockHomeViewModel : StockHomeViewModel by viewModels()

    private lateinit var hostAdapter: StockHostAdapter

    override fun bind() {
        bindStocksToWatch()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindHostRecyclerView()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindStockState()
    }

    private fun bindStocksToWatch() {
        stockHomeViewModel.onEvent(StockHomeEvent.GetStocksToWatch)
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                stockHomeViewModel.onEvent(StockHomeEvent.LogOut)
            }
        }
    }

    private fun bindHostRecyclerView() {
        val onStockClick: (Stock) -> Unit = { stock ->
            handleItemClick(stock)
        }

        val onBestStocksViewMoreClick = {
            handleViewMoreClicked(Stock.PerformingType.BEST_PERFORMING)
        }

        val onWorstStocksViewMoreClick = {
            handleViewMoreClicked(Stock.PerformingType.WORST_PERFORMING)
        }

        val onActiveStocksViewMoreClick = {
            handleViewMoreClicked(Stock.PerformingType.ACTIVE_PERFORMING)
        }

        hostAdapter = StockHostAdapter(
            onStockClick = onStockClick,
            onBestStocksViewMoreClick = onBestStocksViewMoreClick,
            onWorstStocksViewMoreClick = onWorstStocksViewMoreClick,
            onActiveStocksViewMoreClick = onActiveStocksViewMoreClick
        )

        binding.hostRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = hostAdapter
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stockHomeViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is StockHomeNavigationEvent.LogOut -> logOut()
                        is StockHomeNavigationEvent.NavigateToExtendedStockList -> navigateToExtensiveListFragment(stockType = event.stockType)
                        is StockHomeNavigationEvent.NavigateToDetailsPage -> navigateToDetailsFragment(stock = event.stock)
                    }
                }
            }
        }
    }

    private fun bindStockState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stockHomeViewModel.stockState.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state: StockListState) {
        binding.progressBar.isVisible = state.isLoading

        state.errorMessage?.let { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showError(errorMessage)
            }
        } ?: run {
            if (!state.isLoading) {
                hostAdapter.updateData(
                    state.bestPerformingStocks ?: emptyList(),
                    state.worstPerformingStocks ?: emptyList(),
                    state.activePerformingStocks ?: emptyList()
                )
            }
        }
    }

    private fun handleItemClick(stock : Stock) {
    }

    private fun handleViewMoreClicked(stockType: Stock.PerformingType) {
        stockHomeViewModel.onEvent(StockHomeEvent.NavigateToExtensiveListPage(stockType = stockType))
    }
    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun logOut() {
        findNavController().navigate(R.id.action_stockHomeFragment_to_homeFragment)
    }

    private fun navigateToExtensiveListFragment(stockType : Stock.PerformingType) {
        //here logic for navigating with the stock-type action so i know which one to call in the next fragment
    }

    private fun navigateToDetailsFragment(stock: Stock) {
        //here logic for navigating over to details fragment
    }
}
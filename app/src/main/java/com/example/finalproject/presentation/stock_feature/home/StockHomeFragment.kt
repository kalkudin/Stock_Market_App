package com.example.finalproject.presentation.stock_feature.home

import android.view.View
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
import com.example.finalproject.presentation.stock_feature.home.adapter.StockHostRecyclerAdapter
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.home.state.StockListState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockHomeFragment :
    BaseFragment<FragmentStockHomeLayoutBinding>(FragmentStockHomeLayoutBinding::inflate) {

    private val stockHomeViewModel: StockHomeViewModel by viewModels()

    private lateinit var hostAdapter: StockHostRecyclerAdapter

    override fun bind() {
        bindStocksAndUserData()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindHostRecyclerView()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindStockState()
    }

    private fun bindStocksAndUserData() {
        stockHomeViewModel.onEvent(StockHomeEvent.GetStocksAndUserData)
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

        val onViewMoreClick: (Stock.PerformingType) -> Unit = { type ->
            handleViewMoreClicked(type)
        }

        val onAddFundsCLicked: () -> Unit = {
            handleAddFundsClicked()
        }

        hostAdapter = StockHostRecyclerAdapter(
            onStockClick = onStockClick,
            onViewMoreClick = onViewMoreClick,
            onAddFundsCLicked
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
                    when (event) {
                        is StockHomeNavigationEvent.LogOut -> logOut()
                        is StockHomeNavigationEvent.NavigateToDetailsPage -> navigateToDetailsFragment(stock = event.stock)
                        is StockHomeNavigationEvent.NavigateToExtendedStockList -> navigateToExtensiveListFragment(stockType = event.stockType)
                        is StockHomeNavigationEvent.NavigateToFundsPage -> navigateToAddFundsFragment()
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
        binding.hostRecyclerView.visibility = View.GONE
        binding.progressBar.isVisible = state.isLoading

        state.errorMessage?.let { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                showError(errorMessage)
            }
        }

        if (!state.isLoading) {
            binding.hostRecyclerView.visibility = View.VISIBLE
            hostAdapter.updateData(
                state.bestPerformingStocks ?: emptyList(),
                state.worstPerformingStocks ?: emptyList(),
                state.activePerformingStocks ?: emptyList(),
                state.userFunds ?: "0.0"
            )
        }
    }

    private fun handleItemClick(stock: Stock) {
        stockHomeViewModel.onEvent(StockHomeEvent.NavigateToStockDetailsPage(stock = stock))
    }

    private fun handleViewMoreClicked(stockType: Stock.PerformingType) {
        stockHomeViewModel.onEvent(StockHomeEvent.NavigateToExtensiveListPage(stockType = stockType))
    }

    private fun handleAddFundsClicked() {
        stockHomeViewModel.onEvent(StockHomeEvent.NavigateToAddFundsFragment)
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun logOut() {
        findNavController().navigate(R.id.action_stockHomeFragment_to_homeFragment)
    }

    private fun navigateToExtensiveListFragment(stockType: Stock.PerformingType) {
        findNavController().navigate(
            StockHomeFragmentDirections.actionStockHomeFragmentToExtensiveStocksToWatchFragment(
                type = stockType
            )
        )
    }

    private fun navigateToDetailsFragment(stock: Stock) {
        findNavController().navigate(
            StockHomeFragmentDirections.actionStockHomeFragmentToCompanyDetailsFragment(
                symbol = stock.symbol
            )
        )
    }

    private fun navigateToAddFundsFragment() {

    }
}
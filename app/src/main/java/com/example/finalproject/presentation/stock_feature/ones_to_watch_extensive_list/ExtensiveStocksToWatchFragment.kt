package com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentExtensiveStockListLayoutBinding
import com.example.finalproject.databinding.FragmentStockHomeLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.home.StockHomeFragmentDirections
import com.example.finalproject.presentation.stock_feature.home.event.StockHomeEvent
import com.example.finalproject.presentation.stock_feature.home.model.Stock
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.adapter.StockExtensiveListAdapter
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.event.ExtensiveListEvent
import com.example.finalproject.presentation.stock_feature.ones_to_watch_extensive_list.state.ExtensiveStockState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExtensiveStocksToWatchFragment : BaseFragment<FragmentExtensiveStockListLayoutBinding>(FragmentExtensiveStockListLayoutBinding::inflate){

    private val extensiveStocksToWatchViewModel: ExtensiveStocksToWatchViewModel by viewModels()

    private lateinit var stockExtensiveListAdapter: StockExtensiveListAdapter

    override fun bind() {
        extractTypeAndBindStocks()
        bindAdapters()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindExtensiveStocksFlow()
    }

    private fun bindAdapters() {
        stockExtensiveListAdapter = StockExtensiveListAdapter { stock ->
            handleItemClicked(stock)
        }
        binding.rvStockList.adapter = stockExtensiveListAdapter
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                extensiveStocksToWatchViewModel.onEvent(ExtensiveListEvent.NavigateToStockHomeFragment)
            }
        }
    }

    private fun extractTypeAndBindStocks() {
        val args: ExtensiveStocksToWatchFragmentArgs by navArgs()
        val performingType = args.type
        extensiveStocksToWatchViewModel.onEvent(ExtensiveListEvent.GetExtensiveStocksList(performingType = performingType))
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                extensiveStocksToWatchViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is ExtensiveStocksNavigationEvent.NavigateToDetails -> navigateToDetails(stock = event.stock)
                        is ExtensiveStocksNavigationEvent.NavigateBack -> navigateBack()
                    }
                }
            }
        }
    }

    private fun bindExtensiveStocksFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                extensiveStocksToWatchViewModel.extensiveStocksFlow.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state : ExtensiveStockState) {
        binding.progressBar.isVisible = state.isLoading

        state.errorMessage?.let { errorMessage ->
            showError(errorMessage = errorMessage)
            binding.progressBar.isVisible = false

        }

        state.extensiveStocks?.let { list ->
            binding.progressBar.isVisible = false
            Log.d("Extensive", "${list.size}")
            stockExtensiveListAdapter.submitList(list)
        }
    }

    private fun handleItemClicked(stock: Stock) {
        extensiveStocksToWatchViewModel.onEvent(ExtensiveListEvent.NavigateToStockDetailsPage(stock = stock))
    }

    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun navigateToDetails(stock: Stock) {
        findNavController().navigate(ExtensiveStocksToWatchFragmentDirections.actionExtensiveStocksToWatchFragmentToCompanyDetailsFragment(symbol = stock.symbol))
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_extensiveStocksToWatchFragment_to_stockHomeFragment)
    }
}
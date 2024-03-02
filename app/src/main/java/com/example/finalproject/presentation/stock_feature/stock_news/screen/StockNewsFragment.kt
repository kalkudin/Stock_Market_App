package com.example.finalproject.presentation.stock_feature.stock_news.screen

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.FragmentStockNewsBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.stock_feature.stock_news.adapters.StockNewsPagingAdapter
import com.example.finalproject.presentation.stock_feature.stock_news.event.StockNewsEvents
import com.example.finalproject.presentation.stock_feature.stock_news.state.StockNewsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockNewsFragment :
    BaseFragment<FragmentStockNewsBinding>(FragmentStockNewsBinding::inflate) {

    private val viewModel: StockNewsViewModel by viewModels()
    private lateinit var stockNewsAdapter: StockNewsPagingAdapter

    override fun bindViewActionListeners() {
        setupAdapter()
    }

    override fun bindObservers() {
        observeStockNewsState()
    }

    private fun setupAdapter() {
        Log.d("StockNewsFragment", "setupAdapter called")
        stockNewsAdapter = StockNewsPagingAdapter()
        binding.rvStockNews.apply {
            adapter = stockNewsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        viewModel.onEvent(StockNewsEvents.GetStockNews(page = 1))
    }

    private fun observeStockNewsState() {
        Log.d("StockNewsFragment", "observeStockNewsState called")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stockNewsState.collect {
                    handleStockNewsState(it)
                }
            }
        }
    }

    private fun handleStockNewsState(state: StockNewsState) {
        Log.d("StockNewsFragment", "handleStockNewsState called with state: $state")
        state.stockNews?.let { stockNews ->
            Log.d("StockNewsFragment", "Stock news data: $stockNews")
            lifecycleScope.launch {
                stockNewsAdapter.submitData(stockNews)
            }
        }
        state.errorMessage?.let { binding.root.showSnackBar(it) }
        binding.progressBar.isVisible = state.isLoading
    }
}
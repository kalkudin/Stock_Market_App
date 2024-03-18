package com.example.finalproject.presentation.screen.stock_news

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.finalproject.databinding.FragmentStockNewsBinding
import com.example.finalproject.presentation.adapters.stock_news.StockNewsRecyclerAdapter
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.stock_news.StockNewsEvent
import com.example.finalproject.presentation.extension.showSnackBar
import com.example.finalproject.presentation.model.stock_news.News
import com.example.finalproject.presentation.state.stock_news.StockNewsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StockNewsFragment :
    BaseFragment<FragmentStockNewsBinding>(FragmentStockNewsBinding::inflate) {

    private val viewModel: StockNewsViewModel by viewModels()
    private lateinit var stockNewsAdapter: StockNewsRecyclerAdapter

    override fun bind() {
        super.bind()
        stockNewsAdapterSetUp()
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObservers() {
        observeStockNewsState()
        observeNavigateToUrl()
    }

    private fun stockNewsAdapterSetUp() {
        stockNewsAdapter = StockNewsRecyclerAdapter(
            onItemClick = {
                handleNewsItemClick(it)
            }
        )
        binding.rvStockNews.apply {
            adapter = stockNewsAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }
        viewModel.onEvent(StockNewsEvent.GetNewsList)
        stockNewsAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible =
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading

            val errorState = loadState.refresh as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                binding.root.showSnackBar(it.error.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun observeStockNewsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stockNewsState.collect {
                    handleStockNewsState(it)
                }
            }
        }
    }

    private fun observeNavigateToUrl() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToUrl.collect { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(intent)
                    } else {
                        binding.root.showSnackBar("No app can handle the request")
                    }
                }
            }
        }
    }


    private fun handleNewsItemClick(item: News) {
        viewModel.onEvent(StockNewsEvent.NewsItemClick(item = item))
    }

    private suspend fun handleStockNewsState(state: StockNewsState) {
        state.stockNews?.let { stockNewsAdapter.submitData(it) }
        state.errorMessage?.let { binding.root.showSnackBar(it) }
    }
}

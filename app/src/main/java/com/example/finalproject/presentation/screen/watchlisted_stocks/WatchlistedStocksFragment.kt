package com.example.finalproject.presentation.screen.watchlisted_stocks

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentWatchlistedStocksBinding
import com.example.finalproject.presentation.adapters.watchlisted_stocks.WatchlistedStocksRecyclerAdapter
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.watchlisted_stocks.WatchlistedStocksEvent
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.company_details.UserIdModel
import com.example.finalproject.presentation.state.watchlisted_stocks.WatchlistedStocksState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistedStocksFragment :
    BaseFragment<FragmentWatchlistedStocksBinding>(FragmentWatchlistedStocksBinding::inflate) {

    private val viewModel: WatchlistedStocksViewModel by viewModels()
    private lateinit var watchlistedStocksAdapter: WatchlistedStocksRecyclerAdapter

    override fun bind() {
        super.bind()
        watchlistedStocksAdapterSetup()
    }

    override fun bindViewActionListeners() {
        searchListener()
    }

    override fun bindObservers() {
        observeWatchlistedStocks()
        observeWatchlistedStocksNavigationEvents()
    }

    private fun watchlistedStocksAdapterSetup() {
        watchlistedStocksAdapter = WatchlistedStocksRecyclerAdapter(
            onCompanyClick = {
                handleCompanyClick(it)
            },
            onDeleteButtonClick = {
                handleDeleteButtonClick(it)
            }
        )
        binding.rvWatchlisted.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = watchlistedStocksAdapter
        }
        val firebaseUser = Firebase.auth.currentUser
        val userId = firebaseUser?.uid
        if (userId != null) {
            viewModel.onEvent(WatchlistedStocksEvent.GetWatchlistedStocks(UserIdModel(userId)))
        } else {
            Log.d("FavouritesFragment", "Failed to get favourite plants: User not logged in")
        }
    }

    private fun observeWatchlistedStocks() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.watchlistedStocksState.collect { state ->
                    handleWatchlistedStocksState(state)
                }
            }
        }
    }

    private fun observeWatchlistedStocksNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.watchlistedNavigationEvent.collect {
                    handleCompanyListNavigationEvents(it)
                }
            }
        }
    }

    private fun searchListener() {
        binding.etSearch.addTextChangedListener {
            handleSearch(it.toString())
        }
    }

    private fun handleSearch(query: String) {
        viewModel.onEvent(WatchlistedStocksEvent.SearchWatchlistedStocks(query = query))
    }

    private fun handleWatchlistedStocksState(state: WatchlistedStocksState) {
       state.watchlistedStocks?.let {
           watchlistedStocksAdapter.submitList(it)
       }
    }

    private fun handleCompanyListNavigationEvents(event: WatchlistedStocksViewModel.WatchlistedStocksNavigationEvents) {
        when (event) {
            is WatchlistedStocksViewModel.WatchlistedStocksNavigationEvents.NavigateToCompanyDetails -> findNavController().navigate(
                WatchlistedStocksFragmentDirections.actionWatchlistedStocksFragmentToCompanyDetailsFragment(
                    event.symbol
                )
            )
        }
    }

    private fun handleCompanyClick(stock: CompanyDetailsModel) {
        viewModel.onEvent(WatchlistedStocksEvent.StocksItemClick(stock = stock))
    }

    private fun handleDeleteButtonClick(stock: CompanyDetailsModel) {
        viewModel.onEvent(WatchlistedStocksEvent.DeleteWatchlistedStocks(stock, UserIdModel(Firebase.auth.currentUser?.uid!!)))
    }
}
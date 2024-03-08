package com.example.finalproject.presentation.screen.favorites

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFavoriteStocksLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.adapters.profile.FavoriteStocksRecyclerAdapter
import com.example.finalproject.presentation.event.profile.FavoriteStocksEvent
import com.example.finalproject.presentation.state.profile.SavedStocksSuccessState
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteStocksFragment : BaseFragment<FragmentFavoriteStocksLayoutBinding>(FragmentFavoriteStocksLayoutBinding::inflate){

    private val favoriteStocksViewModel : FavoriteStocksViewModel by viewModels()

    private lateinit var favoriteStocksAdapter: FavoriteStocksRecyclerAdapter

    private val stocksToRemoveList: MutableList<String> = mutableListOf()

    override fun bind() {
//        bindStocks()
        bindStocksAdapter()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
//        bindDeleteButton()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindSuccessFlow()
    }

//    private fun bindStocks() {
//        favoriteStocksViewModel.onEvent(FavoriteStocksEvent.GetFavoriteStocks)
//    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener { favoriteStocksViewModel.onEvent(FavoriteStocksEvent.NavigateBack) }
        }
    }

//    private fun bindDeleteButton() {
//        //selection does not include an option to unselect
//        with(binding) {
//            btnDeleteAll.setOnClickListener {
//                favoriteStocksViewModel.onEvent(FavoriteStocksEvent.RemoveSelectedStocks(stocksToRemoveList))
//            }
//        }
//    }

    private fun bindStocksAdapter() {
        with(binding) {
            favoriteStocksAdapter = FavoriteStocksRecyclerAdapter(
                onViewMoreClick = { companyDetailsModel ->
                    handleItemClicked(companyDetailsModel) },
                onStockLongClick = { companyDetailsModel ->
                    handleStockSelected(companyDetailsModel) }
            )
            rvFavStocks.adapter = favoriteStocksAdapter
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteStocksViewModel.navigationFlow.collect { event ->
                    handleNavigation(event)
                }
            }
        }
    }

    private fun bindSuccessFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteStocksViewModel.successFlow.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state : SavedStocksSuccessState) {
        with(binding) {
            if(state.isLoading) showView(view = progressBar)

            state.errorMessage?.let { message ->
                hideView(view = progressBar)
                handleErrorMessage(message)
            }

            state.companyDetails?.let { list ->
                hideView(view = progressBar)
                //show the adapter layout
                //submit the list to the adapter here
                favoriteStocksAdapter.submitList(list)
            }

            if(state.success) {
                hideView(view = progressBar)
            }
        }
    }

    private fun handleNavigation(event : FavoriteStocksNavigationEvent) {
        when(event) {
            is FavoriteStocksNavigationEvent.NavigateBack -> {
                navigateBack()
            }
            is FavoriteStocksNavigationEvent.NavigateToDetails -> {
                navigateToDetailsWithAction(symbol = event.symbol)
            }
        }
    }

    private fun handleItemClicked(stock : CompanyDetailsModel) {
        favoriteStocksViewModel.onEvent(FavoriteStocksEvent.NavigateToDetails(symbol = stock.symbol))
    }

    private fun handleStockSelected(stock : CompanyDetailsModel) {
        stocksToRemoveList.add(stock.symbol)
        Log.d("FavoriteStocksF", stocksToRemoveList.toString())
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_favoriteStocksFragment_to_userProfileFragment)
    }

    private fun navigateToDetailsWithAction(symbol : String) {
        findNavController().navigate(FavoriteStocksFragmentDirections.actionFavoriteStocksFragmentToCompanyDetailsFragment(symbol = symbol))
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun hideView(view : View) {
        view.visibility = View.GONE
    }

    private fun showView(view : View) {
        view.visibility = View.VISIBLE
    }
}
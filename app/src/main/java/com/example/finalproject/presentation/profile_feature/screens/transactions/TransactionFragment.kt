package com.example.finalproject.presentation.profile_feature.screens.transactions

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.finalproject.databinding.FragmentFavoriteStocksLayoutBinding
import com.example.finalproject.databinding.FragmentTransactionsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.profile_feature.event.TransactionEvent
import com.example.finalproject.presentation.profile_feature.state.TransactionState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionsLayoutBinding>(FragmentTransactionsLayoutBinding::inflate) {

    private val transactionsViewModel : TransactionViewModel by viewModels()

    override fun bind() {
        bindAdapter()
        bindTransactions()
    }

    override fun bindViewActionListeners() {
        bindBackBnt()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindTransactionFlow()
    }

    private fun bindBackBnt() {

    }

    private fun bindTransactions() {
        transactionsViewModel.onEvent(TransactionEvent.GetTransactions)
    }

    private fun bindAdapter() {

    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionsViewModel.navigationFlow.collect { event ->
                    handleNavigationFlow(event = event)
                }
            }
        }
    }

    private fun bindTransactionFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionsViewModel.transactionFlow.collect { state ->
                    handleTransactionState(state = state)
                }
            }
        }
    }

    private fun handleNavigationFlow(event : TransactionNavigationFlow) {
        when(event) {
            is TransactionNavigationFlow.NavigateBack -> navigateBack()
        }
    }

    private fun handleTransactionState(state : TransactionState) {
        with(binding) {
            if(state.isLoading) showView(progressBar)

            state.errorMessage?.let { message ->
                hideView(progressBar)
                handleErrorMessage(errorMessage = message) }

            state.transactionList?.let { list ->
                hideView(progressBar)
                //submit the list to the adapter here
            }
        }
    }

    private fun navigateBack() {

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
package com.example.finalproject.presentation.profile_feature.screens.transactions

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFavoriteStocksLayoutBinding
import com.example.finalproject.databinding.FragmentTransactionsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.extension.setOnItemSelected
import com.example.finalproject.presentation.profile_feature.adapter.TransactionRecyclerAdapter
import com.example.finalproject.presentation.profile_feature.event.TransactionEvent
import com.example.finalproject.presentation.profile_feature.state.TransactionState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionsLayoutBinding>(FragmentTransactionsLayoutBinding::inflate) {

    private val transactionsViewModel : TransactionViewModel by viewModels()

    private lateinit var transactionAdapter : TransactionRecyclerAdapter

    override fun bind() {
        bindAdapter()
        bindTransactions()
    }

    override fun bindViewActionListeners() {
        bindBackBnt()
        bindSpinner()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindTransactionFlow()
    }

    private fun bindBackBnt() {
        with(binding) {
            btnBack.setOnClickListener() {
                transactionsViewModel.onEvent(TransactionEvent.NavigateBack)
            }
        }
    }

    private fun bindTransactions() {
        transactionsViewModel.onEvent(TransactionEvent.GetTransactions)
    }

    private fun bindSpinner() {
        with(binding) {
            val sortOptions = resources.getStringArray(R.array.sort_options)
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spinnerSortingOptions.adapter = adapter
            spinnerSortingOptions.setOnItemSelected { selectedItem ->
                sortTransactions(selectedItem)
            }
        }
    }

    private fun bindAdapter() {
        with(binding) {
            transactionAdapter = TransactionRecyclerAdapter()
            rvTransactions.adapter = transactionAdapter
        }
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
                transactionAdapter.submitList(list)
            }
        }
    }

    private fun sortTransactions(sort : String) {
        transactionsViewModel.onEvent(TransactionEvent.SortTransactions(sort = sort))
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_transactionFragment_to_userProfileFragment)
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
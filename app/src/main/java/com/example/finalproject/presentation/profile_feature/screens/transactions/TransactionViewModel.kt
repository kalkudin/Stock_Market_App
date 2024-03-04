package com.example.finalproject.presentation.profile_feature.screens.transactions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.transactions_usecase.GetTransactionUseCase
import com.example.finalproject.presentation.profile_feature.event.TransactionEvent
import com.example.finalproject.presentation.profile_feature.mapper.toPresentation
import com.example.finalproject.presentation.profile_feature.state.TransactionState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val dataStoreUseCases : DataStoreUseCases,
    private val transactionsUseCases: TransactionsUseCases
) : ViewModel(){

    private val _transactionFlow = MutableStateFlow(TransactionState())
    val transactionFlow : StateFlow<TransactionState> = _transactionFlow.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<TransactionNavigationFlow>()
    val navigationFlow : SharedFlow<TransactionNavigationFlow> = _navigationFlow

    fun onEvent(event : TransactionEvent) {
        viewModelScope.launch {
            when(event) {
                is TransactionEvent.NavigateBack -> {navigateBack()}
                is TransactionEvent.GetTransactions -> {getTransactions()}
            }
        }
    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(TransactionNavigationFlow.NavigateBack)
    }

    private suspend fun getTransactions() {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        transactionsUseCases.getTransactionsUseCase(uid = uid).collect { resource ->
            when(resource) {
                is Resource.Error -> {
                    _transactionFlow.update { state ->
                        state.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType)) }
                }
                is Resource.Loading -> {
                    _transactionFlow.update { state ->
                        state.copy(isLoading = true)}
                }
                is Resource.Success -> {
                    _transactionFlow.update {
                        state -> state.copy(isLoading = false, transactionList = resource.data.map { it.toPresentation() }) }
                }
            }
        }
    }
}

sealed class TransactionNavigationFlow {
    data object NavigateBack : TransactionNavigationFlow()
}
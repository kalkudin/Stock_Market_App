package com.example.finalproject.presentation.screen.owned_stocks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.presentation.event.owned_stocks.OwnedStocksEvent
import com.example.finalproject.presentation.mapper.company_details.toDomain
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.model.company_details.UserId
import com.example.finalproject.presentation.model.owned_stocks.OwnedStock
import com.example.finalproject.presentation.state.owned_stocks.OwnedStocksState
import com.example.finalproject.presentation.state.watchlisted_stocks.WatchlistedStocksState
import com.example.finalproject.presentation.util.getErrorMessage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnedStocksViewModel @Inject constructor(
    private val transactionsUseCases: TransactionsUseCases,
    private val dataStoreUseCase: DataStoreUseCases
) : ViewModel() {

    private val _ownedStocksState = MutableStateFlow(OwnedStocksState())
    val ownedStocksState: SharedFlow<OwnedStocksState> get() = _ownedStocksState

    private val _ownedNavigationEvent = MutableSharedFlow<OwnedStocksNavigationEvents>()
    val ownedNavigationEvent: SharedFlow<OwnedStocksNavigationEvents> get() = _ownedNavigationEvent

    fun onEvent(event: OwnedStocksEvent) {
        when (event) {
            is OwnedStocksEvent.GetOwnedStocks -> getOwnedStocks()
            is OwnedStocksEvent.StocksItemClick -> navigateToDetailsPage(event.stock)
            is OwnedStocksEvent.SearchOwnedStocks -> searchOwnedStocks(event.query)
        }
    }

    private fun getOwnedStocks() {
        viewModelScope.launch {
            val uid = dataStoreUseCase.readUserUidUseCase().first()
            transactionsUseCases.getTransactionsUseCase(uid).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val transactions = resource.data
                        val stocks = transactions.map { it.description }.distinct()
                        val ownedStocks = mutableListOf<OwnedStock>()

                        for (stock in stocks) {
                            val stockTransactions = transactions.filter { it.description == stock }
                            val boughtAmount =
                                stockTransactions.filter { it.type == "buy" }.sumOf { it.amount }
                            val soldAmount =
                                stockTransactions.filter { it.type == "sell" }.sumOf { it.amount }
                            val ownedAmount = boughtAmount - soldAmount

                            if (ownedAmount > 0) {
                                ownedStocks.add(OwnedStock(stock, ownedAmount))
                            }
                        }

                        _ownedStocksState.update { currentState ->
                            currentState.copy(ownedStocks = ownedStocks, originalOwnedStocks = ownedStocks)
                        }
                    }

                    is Resource.Error -> {
                        updateErrorMessage(resource.errorType)
                    }

                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: ErrorType) {
        val message = getErrorMessage(errorMessage)
        _ownedStocksState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }


    private fun searchOwnedStocks(query: String) {
        viewModelScope.launch {
            Log.d("OwnedStocksVM", "Search Launched")
            _ownedStocksState.update { currentState ->
                val originalList = currentState.originalOwnedStocks
                Log.d("OwnedStocksVM", originalList.toString())
                val filteredList = if (query.isEmpty()) {
                    originalList
                } else {
                    originalList?.filter { it.symbol.contains(query, true) }
                }
                Log.d("OwnedStocksVM", filteredList.toString())
                currentState.copy(
                    ownedStocks = filteredList,
                    originalOwnedStocks = currentState.originalOwnedStocks
                )
            }
        }
    }

    private fun navigateToDetailsPage(company: OwnedStock) {
        viewModelScope.launch {
            _ownedNavigationEvent.emit(
                OwnedStocksNavigationEvents.NavigateToCompanyDetails(
                    company.symbol)
            )
        }
    }

    sealed interface OwnedStocksNavigationEvents {
        data class NavigateToCompanyDetails(val symbol: String) : OwnedStocksNavigationEvents
    }
}
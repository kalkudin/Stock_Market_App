package com.example.finalproject.presentation.screen.watchlisted_stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.domain.usecase.DataBaseUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.database_usecase.GetWatchlistedStocksForUserUseCase
import com.example.finalproject.presentation.event.watchlisted_stocks.WatchlistedStocksEvent
import com.example.finalproject.presentation.mapper.company_details.toDomain
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.UserId
import com.example.finalproject.presentation.state.watchlisted_stocks.WatchlistedStocksState
import com.example.finalproject.presentation.util.getErrorMessage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistedStocksViewModel @Inject constructor(
    private val getWatchlistedStocksUseCase: GetWatchlistedStocksForUserUseCase,
    private val dataBaseUseCases: DataBaseUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val dataStoreUseCase: DataStoreUseCases
) : ViewModel() {

    private val _watchlistedStocksState = MutableStateFlow(WatchlistedStocksState())
    val watchlistedStocksState: SharedFlow<WatchlistedStocksState> get() = _watchlistedStocksState

    private val _watchlistedNavigationEvent = MutableSharedFlow<WatchlistedStocksNavigationEvents>()
    val watchlistedNavigationEvent: SharedFlow<WatchlistedStocksNavigationEvents> get() = _watchlistedNavigationEvent

    fun onEvent(event: WatchlistedStocksEvent) {
        when (event) {
            is WatchlistedStocksEvent.GetWatchlistedStocks -> getWatchlistedStocks()
            is WatchlistedStocksEvent.StocksItemClick -> navigateToDetailsPage(event.stock)
            is WatchlistedStocksEvent.SearchWatchlistedStocks -> searchWatchlistedStocks(event.query)
            is WatchlistedStocksEvent.DeleteWatchlistedStocks -> deleteWatchlistedStocks(event.stock, event.user)
        }
    }

    private fun getWatchlistedStocks() {
        viewModelScope.launch {
            val firebaseUser = Firebase.auth.currentUser
            val userId = firebaseUser?.uid
            if (userId != null) {
                getWatchlistedStocksUseCase.invoke(UserId(userId).toDomain())
                    .collect { stocks ->
                        _watchlistedStocksState.value =
                            WatchlistedStocksState(watchlistedStocks = stocks.map { it.toPresentation() })
                    }
            } else {
                updateErrorMessage(ErrorType.LocalDatabaseError)
            }
        }
    }

    private fun updateErrorMessage(errorMessage: ErrorType) {
        val message = getErrorMessage(errorMessage)
        _watchlistedStocksState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }


    private fun searchWatchlistedStocks(query: String) {
        viewModelScope.launch {
            _watchlistedStocksState.update { currentState ->
                val originalList = currentState.originalWatchlistedStocks
                    ?: currentState.watchlistedStocks.orEmpty()
                val filteredList = if (query.isEmpty()) {
                    originalList
                } else {
                    originalList.filter { it.companyName.contains(query, true) || it.symbol.contains(query, true) }
                }
                currentState.copy(
                    watchlistedStocks = filteredList,
                    originalWatchlistedStocks = currentState.originalWatchlistedStocks
                        ?: currentState.watchlistedStocks.orEmpty()
                )
            }
        }
    }

    private fun deleteWatchlistedStocks(stock: CompanyDetails, user: UserId) {
        viewModelScope.launch {
            dataBaseUseCases.deleteWatchlistedStocksUseCase.invoke(user.toDomain(), stock.toDomain())
        }
    }

    private fun navigateToDetailsPage(company: CompanyDetails) {
        viewModelScope.launch {
            _watchlistedNavigationEvent.emit(
                WatchlistedStocksNavigationEvents.NavigateToCompanyDetails(
                    company.symbol)
            )
        }
    }

    sealed interface WatchlistedStocksNavigationEvents {
        data class NavigateToCompanyDetails(val symbol: String) : WatchlistedStocksNavigationEvents
    }
}
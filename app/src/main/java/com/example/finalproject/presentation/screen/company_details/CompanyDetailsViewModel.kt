package com.example.finalproject.presentation.screen.company_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.DataBaseUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.company_details_chart_usecase.GetCompanyChartIntradayUseCase
import com.example.finalproject.domain.usecase.company_details_usecase.GetCompanyDetailsUseCase
import com.example.finalproject.presentation.event.company_details.CompanyDetailsEvents
import com.example.finalproject.presentation.mapper.company_details.toDomain
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.company_details.UserIdModel
import com.example.finalproject.presentation.state.company_details.CompanyDetailsState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(
    private val getCompanyDetailsUseCase: GetCompanyDetailsUseCase,
    private val getCompanyChartIntradayUseCase: GetCompanyChartIntradayUseCase,
    private val dataBaseUseCases: DataBaseUseCases,
    private val transactionsUseCases: TransactionsUseCases
) : ViewModel() {

    private val _companyDetailsState = MutableStateFlow(CompanyDetailsState())
    val companyDetailsState: SharedFlow<CompanyDetailsState> get() = _companyDetailsState


    fun onEvent(event: CompanyDetailsEvents) {
        when (event) {
            is CompanyDetailsEvents.GetCompanyDetails -> getCompanyDetails(event.symbol)
            is CompanyDetailsEvents.GetCompanyChartIntraday -> getCompanyChartIntraday(event.interval, event.symbol, event.from, event.to)
            is CompanyDetailsEvents.InsertStocks -> insertStocks(event.stock)
            is CompanyDetailsEvents.InsertUser -> insertUser(event.user)
            is CompanyDetailsEvents.InsertStocksToWatchlist -> insertStocksToWatchlist(event.stock, event.user)
            is CompanyDetailsEvents.DeleteWatchlistedStocks -> deleteWatchlistedStocks(event.stock, event.user)
            is CompanyDetailsEvents.BuyStock -> buyStock(event.userId, event.amount, event.description)
            is CompanyDetailsEvents.SellStock -> sellStock(event.userId, event.amount, event.description)
            is CompanyDetailsEvents.IsStockInWatchlist -> checkIfStockIsInWatchlist(event.userId, event.symbol)
        }
    }

    private fun checkIfStockIsInWatchlist(userId: String, symbol: String) {
        viewModelScope.launch {
            dataBaseUseCases.isStockInWatchListUseCase.invoke(userId, symbol).collect { isStockInWatchlist ->
                _companyDetailsState.update { currentState ->
                    currentState.copy(isStockInWatchlist = isStockInWatchlist)
                }
            }
        }
    }

    private fun sellStock(userId: String, amount: Double, description: String) {
        viewModelScope.launch {
            transactionsUseCases.saveTransactionUseCase.invoke(userId, amount, "sell", description)
            Log.d("CompanyDetailsViewModel", "sellStock: $userId, $amount, $description")
        }
    }

    private fun buyStock(userId: String, amount: Double, description: String) {
        viewModelScope.launch {
            transactionsUseCases.saveTransactionUseCase.invoke(userId, amount, "buy", description)
        }
        Log.d("CompanyDetailsViewModel", "buyStock: $userId, $amount, $description")
    }

    private fun getCompanyDetails(symbol:String){
        viewModelScope.launch {
            handleResource(getCompanyDetailsUseCase.invoke(symbol)) { data ->
                _companyDetailsState.update { currentState ->
                    currentState.copy(companyDetails = data.map { it.toPresentation() })
                }
            }
        }
    }

    private fun getCompanyChartIntraday(interval: String, symbol: String, from: String, to: String) {
        viewModelScope.launch {
            handleResource(getCompanyChartIntradayUseCase.invoke(interval, symbol, from, to)) { data ->
                _companyDetailsState.update { currentState ->
                    currentState.copy(companyChartIntraday = data.map { it.toPresentation() })
                }
            }
        }
    }

    private fun updateErrorMessages(errorMessage: ErrorType) {
        val message = getErrorMessage(errorMessage)
        _companyDetailsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun insertStocks(stock: CompanyDetailsModel) {
        viewModelScope.launch {
            dataBaseUseCases.insertStocksUseCase.invoke(stock.toDomain())
        }
    }

    private fun insertUser(user: UserIdModel) {
        viewModelScope.launch {
            dataBaseUseCases.insertUserUseCase.invoke(user.toDomain())
        }
    }

    private fun insertStocksToWatchlist(stock: CompanyDetailsModel, user: UserIdModel) {
        viewModelScope.launch {
            dataBaseUseCases.insertWatchlistedStocksUseCase.invoke(user.toDomain(), stock.toDomain())
        }
    }

    private fun deleteWatchlistedStocks(stock: CompanyDetailsModel, user: UserIdModel) {
        viewModelScope.launch {
            dataBaseUseCases.deleteWatchlistedStocksUseCase.invoke(user.toDomain(), stock.toDomain())
        }
    }

    private fun <T> handleResource(resourceFlow: Flow<Resource<T>>, handleSuccess: (T) -> Unit) {
        viewModelScope.launch {
            resourceFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> handleSuccess(resource.data)
                    is Resource.Error -> updateErrorMessages(resource.errorType)
                    is Resource.Loading -> _companyDetailsState.update { currentState ->
                        currentState.copy(isLoading = resource.loading)
                    }
                }
            }
        }
    }
}





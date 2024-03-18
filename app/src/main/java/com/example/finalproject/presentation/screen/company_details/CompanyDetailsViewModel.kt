package com.example.finalproject.presentation.screen.company_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.DataBaseUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.domain.usecase.company_details_chart_usecase.GetCompanyChartIntradayUseCase
import com.example.finalproject.domain.usecase.company_details_usecase.GetCompanyDetailsUseCase
import com.example.finalproject.presentation.event.company_details.CompanyDetailsEvent
import com.example.finalproject.presentation.mapper.company_details.toDomain
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.model.company_details.CompanyDetails
import com.example.finalproject.presentation.model.company_details.UserId
import com.example.finalproject.presentation.state.company_details.CompanyDetailsState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailsViewModel @Inject constructor(
    private val getCompanyDetailsUseCase: GetCompanyDetailsUseCase,
    private val getCompanyChartIntradayUseCase: GetCompanyChartIntradayUseCase,
    private val dataBaseUseCases: DataBaseUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val userFundsUseCases: UserFundsUseCases
) : ViewModel() {

    private val _companyDetailsState = MutableStateFlow(CompanyDetailsState())
    val companyDetailsState: SharedFlow<CompanyDetailsState> get() = _companyDetailsState


    fun onEvent(event: CompanyDetailsEvent) {
        when (event) {
            is CompanyDetailsEvent.GetCompanyDetails -> getCompanyDetails(event.symbol)
            is CompanyDetailsEvent.GetCompanyChartIntraday -> getCompanyChartIntraday(
                event.interval,
                event.symbol,
                event.from,
                event.to
            )

            is CompanyDetailsEvent.InsertStocks -> insertStocks(event.stock)
            is CompanyDetailsEvent.InsertUser -> insertUser(event.user)
            is CompanyDetailsEvent.InsertStocksToWatchlist -> insertStocksToWatchlist(
                event.stock,
                event.user
            )

            is CompanyDetailsEvent.DeleteWatchlistedStocks -> deleteWatchlistedStocks(
                event.stock,
                event.user
            )

            is CompanyDetailsEvent.BuyStock -> buyStock(
                event.userId,
                event.amount,
                event.description
            )

            is CompanyDetailsEvent.SellStock -> sellStock(
                event.userId,
                event.amount,
                event.description
            )

            is CompanyDetailsEvent.IsStockInWatchlist -> checkIfStockIsInWatchlist(
                event.userId,
                event.symbol
            )
        }
    }

    private fun checkIfStockIsInWatchlist(userId: String, symbol: String) {
        viewModelScope.launch {
            dataBaseUseCases.isStockInWatchListUseCase.invoke(userId, symbol)
                .collect { isStockInWatchlist ->
                    _companyDetailsState.update { currentState ->
                        currentState.copy(isStockInWatchlist = isStockInWatchlist)
                    }
                }
        }
    }

//    private fun buyStock(userId: String, amount: Double, description: String) {
//        viewModelScope.launch {
//            if (amount > 0) {
//                userFundsUseCases.retrieveUserFundsUseCase(uid = userId).collect { resource ->
//                    when (resource) {
//                        is Resource.Success -> {
//                            if (resource.data.amount >= amount) {
//                                transactionsUseCases.saveTransactionUseCase(userId, amount, "buy", description).collect {
//                                    _companyDetailsState.update { currentState ->
//                                        currentState.copy(successMessage = "Stock bought successfully")
//                                    }
//                                }
//                            } else {
//                                _companyDetailsState.update { currentState ->
//                                    currentState.copy(errorMessage = "Insufficient funds to buy stock")
//                                }
//                            }
//                        }
//                        is Resource.Error -> updateErrorMessages(resource.errorType)
//                        else -> updateErrorMessages(ErrorType.UnknownError("Unknown error occurred"))
//                    }
//                }
//            } else {
//                _companyDetailsState.update { currentState ->
//                    currentState.copy(errorMessage = "Number of stocks to buy should be greater than zero")
//                }
//            }
//        }
//    }
//
//    private fun sellStock(userId: String, amount: Double, description: String) {
//        viewModelScope.launch {
//            if (amount > 0) {
//                transactionsUseCases.getTransactionsUseCase(userId).collect { resource ->
//                    when (resource) {
//                        is Resource.Success -> {
//                            val transactions = resource.data
//                            val stockTransactions = transactions.filter { it.description == description }
//                            val boughtAmount = stockTransactions.filter { it.type == "buy" }.sumOf { it.amount }
//                            val soldAmount = stockTransactions.filter { it.type == "sell" }.sumOf { it.amount }
//                            val ownedAmount = boughtAmount - soldAmount
//
//                            if (ownedAmount >= amount) {
//                                userFundsUseCases.retrieveUserFundsUseCase(uid = userId).collect { resource ->
//                                    when (resource) {
//                                        is Resource.Success -> {
//                                            transactionsUseCases.saveTransactionUseCase(userId, amount, "sell", description).collect {
//                                                _companyDetailsState.update { currentState ->
//                                                    currentState.copy(successMessage = "Stock sold successfully")
//                                                }
//                                            }
//                                        }
//                                        is Resource.Error -> updateErrorMessages(resource.errorType)
//                                        else -> updateErrorMessages(ErrorType.UnknownError("Unknown error occurred"))
//                                    }
//                                }
//                            } else {
//                                _companyDetailsState.update { currentState ->
//                                    currentState.copy(errorMessage = "Insufficient stock to sell")
//                                }
//                            }
//                        }
//                        is Resource.Error -> updateErrorMessages(resource.errorType)
//                        else -> updateErrorMessages(ErrorType.UnknownError("Unknown error occurred"))
//                    }
//                }
//            } else {
//                _companyDetailsState.update { currentState ->
//                    currentState.copy(errorMessage = "Number of stocks to sell should be greater than zero")
//                }
//            }
//        }
//    }

    private fun buyStock(userId: String, amount: Double, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (amount > 0) {
                handleTransaction(userId, amount, description, "buy")
            } else {
                updateErrorMessage("Number of stocks to buy should be greater than zero")
            }
        }
    }

    private fun sellStock(userId: String, amount: Double, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (amount > 0) {
                handleTransaction(userId, amount, description, "sell")
            } else {
                updateErrorMessage("Number of stocks to sell should be greater than zero")
            }
        }
    }

    private suspend fun handleTransaction(
        userId: String,
        amount: Double,
        description: String,
        transactionType: String
    ) {
        val resource = userFundsUseCases.retrieveUserFundsUseCase(uid = userId).first()
        when (resource) {
            is Resource.Success -> {
                val userFunds = resource.data.amount
                if (userFunds >= amount) {
                    transactionsUseCases.saveTransactionUseCase(
                        userId,
                        amount,
                        transactionType,
                        description
                    ).collect {
                        updateSuccessMessage("Stock $transactionType successfully")
                    }
                } else {
                    updateErrorMessage("Insufficient funds to $transactionType stock")
                }
            }
            is Resource.Error -> updateErrorMessages(resource.errorType)
            else -> updateErrorMessage("Unknown error occurred")
        }
    }

    private fun updateSuccessMessage(message: String) {
        _companyDetailsState.update { currentState ->
            currentState.copy(successMessage = message)
        }
    }

    private fun updateErrorMessage(message: String) {
        _companyDetailsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }
    //

    private fun getCompanyDetails(symbol: String) {
        viewModelScope.launch {
            handleResource(getCompanyDetailsUseCase.invoke(symbol)) { data ->
                _companyDetailsState.update { currentState ->
                    currentState.copy(companyDetails = data.map { it.toPresentation() })
                }
            }
        }
    }

    private fun getCompanyChartIntraday(
        interval: String,
        symbol: String,
        from: String,
        to: String
    ) {
        viewModelScope.launch {
            handleResource(
                getCompanyChartIntradayUseCase.invoke(
                    interval,
                    symbol,
                    from,
                    to
                )
            ) { data ->
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

    private fun insertStocks(stock: CompanyDetails) {
        viewModelScope.launch {
            dataBaseUseCases.insertStocksUseCase.invoke(stock.toDomain())
        }
    }

    private fun insertUser(user: UserId) {
        viewModelScope.launch {
            dataBaseUseCases.insertUserUseCase.invoke(user.toDomain())
        }
    }

    private fun insertStocksToWatchlist(stock: CompanyDetails, user: UserId) {
        viewModelScope.launch {
            dataBaseUseCases.insertWatchlistedStocksUseCase.invoke(
                user.toDomain(),
                stock.toDomain()
            )
        }
    }

    private fun deleteWatchlistedStocks(stock: CompanyDetails, user: UserId) {
        viewModelScope.launch {
            dataBaseUseCases.deleteWatchlistedStocksUseCase.invoke(
                user.toDomain(),
                stock.toDomain()
            )
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





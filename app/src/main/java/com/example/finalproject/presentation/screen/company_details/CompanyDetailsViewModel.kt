package com.example.finalproject.presentation.screen.company_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.common.SuccessType
import com.example.finalproject.domain.model.user_funds.GetUserFunds
import com.example.finalproject.domain.usecase.DataBaseUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
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
import com.example.finalproject.presentation.util.getSuccessMessage
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val dataStoreUseCase: DataStoreUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val userFundsUseCases: UserFundsUseCases
) : ViewModel() {

    private val _companyDetailsState = MutableStateFlow(CompanyDetailsState())
    val companyDetailsState: SharedFlow<CompanyDetailsState> get() = _companyDetailsState

    //
    private var amount : Double = 0.0

    init {
        viewModelScope.launch {
            val uid = dataStoreUseCase.readUserUidUseCase().first()

            userFundsUseCases.retrieveUserFundsUseCase(uid).collect { resource ->
                when (resource) {
                    is Resource.Success -> amount = resource.data.amount
                    else -> {}
                }
            }
        }

    }
    //

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

            is CompanyDetailsEvent.ResetFlow -> resetFlow()
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
                    is Resource.Error -> updateErrorMessage(resource.errorType)
                    is Resource.Loading -> _companyDetailsState.update { currentState ->
                        currentState.copy(isLoading = resource.loading)
                    }
                }
            }
        }
    }
    private fun buyStock(userId: String, transactionAmount: Double, description: String) {
        viewModelScope.launch {
            Log.d("buyStock", "Function called with userId: $userId, amount: $transactionAmount, description: $description")
            if (transactionAmount > 0) {
                if (amount >= transactionAmount) {
                    saveTransaction(userId, transactionAmount, description, "buy")
                    removeUserFunds(userId, transactionAmount)
                    Log.d("buyStock", "RemoveUserFundsUseCase called with userId: $userId, amount: $transactionAmount")
                } else {
                    updateErrorMessage(ErrorType.InsufficientFunds)
                    Log.d("buyStock", "Insufficient funds to buy stock")
                }
            } else {
                updateErrorMessage(ErrorType.AmountGreaterThanZeroToBuy)
                Log.d("buyStock", "Amount must be greater than zero to buy")
            }
        }
    }

    private fun sellStock(userId: String, amount: Double, description: String) {
        viewModelScope.launch {
            Log.d("sellStock", "Function called with userId: $userId, amount: $amount, description: $description")
            if (amount > 0) {
                transactionsUseCases.getTransactionsUseCase(userId).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val transactions = resource.data
                            val stockTransactions = transactions.filter { it.description == description }
                            val boughtAmount = stockTransactions.filter { it.type == "buy" }.sumOf { it.amount }
                            val soldAmount = stockTransactions.filter { it.type == "sell" }.sumOf { it.amount }
                            val ownedAmount = boughtAmount - soldAmount

                            if (ownedAmount >= amount) {
                                saveTransaction(userId, amount, description, "sell")
                                addUserFunds(userId, amount)
                                Log.d("sellStock", "AddUserFundsUseCase called with userId: $userId, amount: $amount")
                            } else {
                                updateErrorMessage(ErrorType.InsufficientStocks)
                                Log.d("sellStock", "Insufficient stocks to sell")
                            }
                        }
                        is Resource.Error -> {
                            updateErrorMessage(resource.errorType)
                            Log.d("sellStock", "Error occurred: ${resource.errorType}")
                        }
                        is Resource.Loading -> {}
                    }
                }
            } else {
                updateErrorMessage(ErrorType.AmountGreaterThanZeroToSell)
                Log.d("sellStock", "Amount must be greater than zero to sell")
            }
        }
    }

    private suspend fun removeUserFunds(userId: String, amount: Double) {
        userFundsUseCases.removeUserFundsUseCase.invoke(GetUserFunds(userId, amount)).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("removeUserFunds", "Funds removed successfully")
                }
                is Resource.Error -> {
                    updateErrorMessage(resource.errorType)
                    Log.d("removeUserFunds", "Error occurred: ${resource.errorType}")
                }
                is Resource.Loading -> {}
            }
        }
    }

    private suspend fun addUserFunds(userId: String, amount: Double) {
        userFundsUseCases.addFundsUseCase.invoke(userId, amount.toString()).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("addUserFunds", "Funds added successfully")
                }
                is Resource.Error -> {
                    updateErrorMessage(resource.errorType)
                    Log.d("addUserFunds", "Error occurred: ${resource.errorType}")
                }
                is Resource.Loading -> {}
            }
        }
    }

    private suspend fun saveTransaction(userId: String, amount: Double, description: String, type: String) {
        transactionsUseCases.saveTransactionUseCase(uid = userId, amount = amount, description = description, type = type).collect {
            updateSuccessMessage(SuccessType.TransactionSuccessful)
            Log.d("saveTransaction", "Transaction successful")
        }
    }


    private fun updateSuccessMessage(successMessage: SuccessType) {
        val message = getSuccessMessage(successMessage)
        _companyDetailsState.update { currentState ->
            currentState.copy(successMessage = message)
        }
    }

    private fun updateErrorMessage(errorMessage: ErrorType) {
        val message = getErrorMessage(errorMessage)
        _companyDetailsState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun resetFlow() {
        _companyDetailsState.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorMessage = null,
                successMessage = null
            )
        }
    }
}






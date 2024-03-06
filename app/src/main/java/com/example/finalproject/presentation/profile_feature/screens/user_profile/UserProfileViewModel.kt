package com.example.finalproject.presentation.profile_feature.screens.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.SavedStocksUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.domain.usecase.UserFundsUseCases
import com.example.finalproject.domain.usecase.credit_card_usecase.GetUserCreditCardsUseCase
import com.example.finalproject.presentation.profile_feature.event.UserProfileEvent
import com.example.finalproject.presentation.profile_feature.mapper.handleResourceUpdate
import com.example.finalproject.presentation.profile_feature.mapper.toPresentation
import com.example.finalproject.presentation.profile_feature.state.ProfileState
import com.example.finalproject.presentation.stock_feature.company_details.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.funds.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val creditCardsUseCases : CreditCardUseCases,
    private val savedStocksUseCases: SavedStocksUseCases
) : ViewModel(){

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState : StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<UserProfileNavigationEvent>()
    val navigationFlow : SharedFlow<UserProfileNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: UserProfileEvent){
        viewModelScope.launch {
            when(event) {
                is UserProfileEvent.AddFunds -> {
                    handleNavigationEvent(UserProfileNavigationEvent.NavigateToAddFunds)
                }
                is UserProfileEvent.NavigateToFavorites -> {
                    handleNavigationEvent(UserProfileNavigationEvent.NavigateToFavorites)
                }
                is UserProfileEvent.UpdateProfile -> {
                    handleNavigationEvent(UserProfileNavigationEvent.UpdateProfile)
                }
                is UserProfileEvent.ViewTradingHistory -> {
                    handleNavigationEvent(UserProfileNavigationEvent.OpenTradingHistory)
                }
                is UserProfileEvent.RetrieveProfileData -> {
                    retrieveProfileData()
                }
                is UserProfileEvent.RemoveCard -> {
                    removeCard(creditCard = event.creditCard)
                }
            }
        }
    }

    private fun retrieveProfileData() {
        viewModelScope.launch {
            val uid = dataStoreUseCases.readUserUidUseCase().first()
            _profileState.update { it.copy(isLoading = true) }

            val transactionsJob = async {
                transactionsUseCases.getTransactionsUseCase(uid).collect { result ->
                    handleResourceUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { transactions -> this.copy(transactionList = transactions.take(3).map { it.toPresentation() }) },
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val cardsJob = async {
                creditCardsUseCases.getUserCreditCardsUseCase(uid).collect { result ->
                    handleResourceUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { cards -> this.copy(cardList = cards.map { it.toPresentation() }) },
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val savedStocksJob = async {
                savedStocksUseCases.getSavedStocksUseCase(uid).collect { result ->
                    handleResourceUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { stocks -> this.copy(favoriteStockList = stocks.take(6).map { it.toPresentation() }) },
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            awaitAll(transactionsJob, cardsJob, savedStocksJob)
            _profileState.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun removeCard(creditCard: CreditCard) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()

        creditCardsUseCases.removeUserCreditCardUseCase(cardId = creditCard.id, uid = uid).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _profileState.update { state ->
                        state.copy(cardList = state.cardList?.filterNot { it.id == creditCard.id }) }
                }
                is Resource.Error -> {
                    _profileState.update { state -> state.copy(errorMessage = getErrorMessage(resource.errorType)) }
                }
                else -> {}
            }
        }
    }

    private suspend fun handleNavigationEvent(event : UserProfileNavigationEvent) {
        _navigationFlow.emit(event)
    }
}

sealed class UserProfileNavigationEvent {
    data object NavigateToAddFunds : UserProfileNavigationEvent()
    data object OpenCreditCardManager : UserProfileNavigationEvent()
    data object NavigateToFavorites : UserProfileNavigationEvent()
    data object UpdateProfile : UserProfileNavigationEvent()
    data object OpenTradingHistory : UserProfileNavigationEvent()
}
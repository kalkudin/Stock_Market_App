package com.example.finalproject.presentation.screen.user_profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.CreditCardUseCases
import com.example.finalproject.domain.usecase.DataBaseUseCases
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.domain.usecase.ImageUseCases
import com.example.finalproject.domain.usecase.TransactionsUseCases
import com.example.finalproject.presentation.event.profile.UserProfileEvent
import com.example.finalproject.presentation.mapper.base.handleStateUpdate
import com.example.finalproject.presentation.mapper.company_details.toDomain
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.mapper.credit_card.toPresentation
import com.example.finalproject.presentation.mapper.profile.toPresentation
import com.example.finalproject.presentation.model.company_details.UserId
import com.example.finalproject.presentation.model.credit_card.CreditCard
import com.example.finalproject.presentation.state.profile.ProfileState
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
    private val transactionsUseCases: TransactionsUseCases,
    private val creditCardsUseCases: CreditCardUseCases,
    private val dataBaseUseCases: DataBaseUseCases,
    private val imageUseCases: ImageUseCases
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<UserProfileNavigationEvent>()
    val navigationFlow: SharedFlow<UserProfileNavigationEvent> = _navigationFlow.asSharedFlow()

    fun onEvent(event: UserProfileEvent) {
        viewModelScope.launch {
            when (event) {
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

                is UserProfileEvent.UploadImageToFireBase -> {
                    uploadImage(uri = event.uri)
                }
            }
        }
    }

    private fun retrieveProfileData() {
        viewModelScope.launch {
            val uid = dataStoreUseCases.readUserUidUseCase().first()

            _profileState.update { it.copy(isLoading = true) }

            val profilePictureJob = async {
                imageUseCases.retrieveImageUseCase(uid = uid).collect { result  ->
                    handleStateUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { uri -> this.copy(profileImage = uri) },
                        onError = { this.copy(errorMessage = null) }
                    )
                }
            }

            val transactionsJob = async {
                transactionsUseCases.getTransactionsUseCase(uid).collect { result ->
                    handleStateUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { transactions -> this.copy(transactionList = transactions.take(3).map { it.toPresentation() }) },
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val cardsJob = async {
                creditCardsUseCases.getUserCreditCardsUseCase(uid).collect { result ->
                    handleStateUpdate(
                        resource = result,
                        stateFlow = _profileState,
                        onSuccess = { cards -> this.copy(cardList = cards.map { it.toPresentation() }) },
                        onError = { errorMessage -> this.copy(errorMessage = errorMessage) }
                    )
                }
            }

            val savedStocksJob = async {
                dataBaseUseCases.getWatchlistedStocksForUserUseCase.invoke(UserId(uid).toDomain()).collect { stocks ->
                    _profileState.value = ProfileState(favoriteStockList = stocks.take(5).map { it.toPresentation() }) }
            }

            awaitAll(transactionsJob, cardsJob, savedStocksJob, profilePictureJob)

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

    private suspend fun uploadImage(uri : Uri) {
        val uid = dataStoreUseCases.readUserUidUseCase().first()
        imageUseCases.uploadImageUseCase(uri = uri, uid = uid).collect() {

        }
    }

    private suspend fun handleNavigationEvent(event: UserProfileNavigationEvent) {
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
package com.example.finalproject.presentation.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.DataStoreUseCases
import com.example.finalproject.presentation.event.profile.FavoriteStocksEvent
import com.example.finalproject.presentation.state.profile.SavedStocksSuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteStocksViewModel @Inject constructor(
    private val dataStoreUseCases: DataStoreUseCases,
//    private val savedStocksUseCases : SavedStocksUseCases,
) : ViewModel() {

    private val _navigationFlow = MutableSharedFlow<FavoriteStocksNavigationEvent>()
    val navigationFlow : SharedFlow<FavoriteStocksNavigationEvent> = _navigationFlow.asSharedFlow()

    private val _successFlow = MutableStateFlow(SavedStocksSuccessState())
    val successFlow : StateFlow<SavedStocksSuccessState> = _successFlow.asStateFlow()

    fun onEvent(event : FavoriteStocksEvent) {
        viewModelScope.launch {
            when(event) {
//                is FavoriteStocksEvent.GetFavoriteStocks -> {getSavedStocks()}
//                is FavoriteStocksEvent.RemoveSelectedStocks -> {removeStocks(symbols = event.symbols)}
                is FavoriteStocksEvent.NavigateBack -> {navigateBack()}
                is FavoriteStocksEvent.NavigateToDetails -> {navigateToDetails(symbol = event.symbol)}
            }
        }
    }

//    private suspend fun getSavedStocks() {
//        val uid = dataStoreUseCases.readUserUidUseCase().first()
//
//        savedStocksUseCases.getSavedStocksUseCase(uid = uid).collect { resource ->
//            when(resource) {
//                is Resource.Error -> {
//                    _successFlow.update { state -> state.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType)) }
//                }
//                is Resource.Loading -> {
//                    _successFlow.update { state -> state.copy(isLoading = true) }
//                }
//                is Resource.Success -> {
//                    _successFlow.update { state -> state.copy(isLoading = false, companyDetails = resource.data.map { item -> item.toPresentation() }) }
//                }
//            }
//        }
//    }

//    private suspend fun removeStocks(symbols : List<String>) {
//        val uid = dataStoreUseCases.readUserUidUseCase().first()
//
//        savedStocksUseCases.removeSavedStockUseCase(uid = uid, symbols = symbols).collect { resource ->
//            when(resource) {
//                is Resource.Error -> {
//                    _successFlow.update { state -> state.copy(isLoading = false, errorMessage = getErrorMessage(resource.errorType)) }
//                }
//                is Resource.Loading -> {
//                    _successFlow.update { state -> state.copy(isLoading = true) }
//                }
//                is Resource.Success -> {
//                    _successFlow.update { state ->
//                        val updatedCompanyDetails = state.companyDetails?.filterNot { companyDetail -> symbols.contains(companyDetail.symbol) }
//                        state.copy(isLoading = false, success = resource.data, companyDetails = updatedCompanyDetails)
//                    }
//                }
//            }
//        }
//    }

    private suspend fun navigateBack() {
        _navigationFlow.emit(FavoriteStocksNavigationEvent.NavigateBack)
    }

    private suspend fun navigateToDetails(symbol : String) {
        _navigationFlow.emit(FavoriteStocksNavigationEvent.NavigateToDetails(symbol = symbol))
    }
}
sealed class FavoriteStocksNavigationEvent {
    data class NavigateToDetails(val symbol : String) : FavoriteStocksNavigationEvent()
    data object NavigateBack : FavoriteStocksNavigationEvent()
}
package com.example.finalproject.presentation.stock_feature.company_list.screen.company_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.company_list_usecase.GetCompanyListUseCase
import com.example.finalproject.presentation.stock_feature.company_list.event.CompanyListEvents
import com.example.finalproject.presentation.stock_feature.company_list.mapper.toPresentation
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel
import com.example.finalproject.presentation.stock_feature.company_list.state.CompanyListState
import com.example.finalproject.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val getCompanyListUseCase: GetCompanyListUseCase,
) : ViewModel() {

    private val _companyListState = MutableStateFlow(CompanyListState())
    val companyListState:SharedFlow<CompanyListState> get() = _companyListState

    private val _companyListNavigationEvent = MutableSharedFlow<CompanyListNavigationEvents>()
    val companyListNavigationEvent: SharedFlow<CompanyListNavigationEvents> get() = _companyListNavigationEvent

    fun onEvent(event: CompanyListEvents) {
        when (event) {
            is CompanyListEvents.GetCompanyList -> getCompanyList()
            is CompanyListEvents.ListSearch -> onListSearch(event.query)
            is CompanyListEvents.CompanyItemClick -> onCompanyListItemClick(event.company)
        }
    }

    private fun getCompanyList(){
        viewModelScope.launch {
            handleResource(getCompanyListUseCase.invoke()) { data ->
                _companyListState.update { currentState ->
                    currentState.copy(companyList = data.map { it.toPresentation() })
                }
            }
        }
    }

    private fun updateErrorMessages(errorMessage: ErrorType) {
        val message = getErrorMessage(errorMessage)
        _companyListState.update { currentState ->
            currentState.copy(errorMessage = message)
        }
    }

    private fun onCompanyListItemClick(company: CompanyListModel) {
        viewModelScope.launch {
            _companyListNavigationEvent.emit(
                CompanyListNavigationEvents.NavigateToCompanyDetails(
                    company.symbol
                )
            )
        }
    }

    private fun onListSearch(query: String) {
        viewModelScope.launch {
            _companyListState.update { currentState ->
                val originalList = currentState.originalCompanyList ?: currentState.companyList.orEmpty()
                val filteredList = if (query.isEmpty()) {
                    originalList
                } else {
                    originalList.filter { it.name.contains(query, true) || it.symbol.contains(query, true) }
                }
                currentState.copy(
                    companyList = filteredList,
                    originalCompanyList = currentState.originalCompanyList ?: currentState.companyList.orEmpty()
                )
            }
        }
    }


    private fun <T> handleResource(resourceFlow: Flow<Resource<T>>, handleSuccess: (T) -> Unit) {
        viewModelScope.launch {
            resourceFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> handleSuccess(resource.data)
                    is Resource.Error -> updateErrorMessages(resource.errorType)
                    is Resource.Loading -> _companyListState.update { currentState ->
                        currentState.copy(isLoading = resource.loading)
                    }
                }
            }
        }
    }

    sealed interface CompanyListNavigationEvents {
        data class NavigateToCompanyDetails(val symbol: String) : CompanyListNavigationEvents
    }
}
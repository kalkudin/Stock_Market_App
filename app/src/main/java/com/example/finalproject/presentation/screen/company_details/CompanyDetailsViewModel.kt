package com.example.finalproject.presentation.screen.company_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.usecase.company_details_chart_usecase.GetCompanyChartIntradayUseCase
import com.example.finalproject.domain.usecase.company_details_usecase.GetCompanyDetailsUseCase
import com.example.finalproject.presentation.event.company_details.CompanyDetailsEvents
import com.example.finalproject.presentation.mapper.company_details.toPresentation
import com.example.finalproject.presentation.state.company_details.CompanyDetailsState
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
class CompanyDetailsViewModel @Inject constructor(
    private val getCompanyDetailsUseCase: GetCompanyDetailsUseCase,
    private val getCompanyChartIntradayUseCase: GetCompanyChartIntradayUseCase
) : ViewModel() {

    private val _companyDetailsState = MutableStateFlow(CompanyDetailsState())
    val companyDetailsState: SharedFlow<CompanyDetailsState> get() = _companyDetailsState

    fun onEvent(event: CompanyDetailsEvents) {
        when (event) {
            is CompanyDetailsEvents.GetCompanyDetails -> getCompanyDetails(event.symbol)
            is CompanyDetailsEvents.GetCompanyChartIntraday -> getCompanyChartIntraday(event.interval, event.symbol, event.from, event.to)
        }
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





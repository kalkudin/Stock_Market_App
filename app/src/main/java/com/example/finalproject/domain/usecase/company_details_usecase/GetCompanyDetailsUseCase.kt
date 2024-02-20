package com.example.finalproject.domain.usecase.company_details_usecase

import com.example.finalproject.domain.repository.company_details.CompanyDetailsRepository
import javax.inject.Inject

class GetCompanyDetailsUseCase @Inject constructor(
    private val companyDetailsRepository: CompanyDetailsRepository
) {
    suspend operator fun invoke(symbol:String) = companyDetailsRepository.getCompanyDetailsRemotely(symbol)
}
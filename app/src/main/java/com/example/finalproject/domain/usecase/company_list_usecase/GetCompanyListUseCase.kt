package com.example.finalproject.domain.usecase.company_list_usecase

import com.example.finalproject.domain.repository.company_list.CompanyListRepository
import javax.inject.Inject

class GetCompanyListUseCase @Inject constructor(
    private val companyListRepository: CompanyListRepository
) {
    //later this will be changed to fetch data depending on the network state of the application
    suspend operator fun invoke() = companyListRepository.getCompanyListRemotely()
}
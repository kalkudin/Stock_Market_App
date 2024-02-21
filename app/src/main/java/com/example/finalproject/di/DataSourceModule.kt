package com.example.finalproject.di

import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.remote.datasource.company_details.RemoteCompanyDetailsDataSourceImpl
import com.example.finalproject.data.remote.datasource.company_list.RemoteCompanyListDataSourceImpl
import com.example.finalproject.data.remote.service.company_details.CompanyDetailsApiService
import com.example.finalproject.data.remote.service.company_list.CompanyListApiService
import com.example.finalproject.domain.datasource.company_details.RemoteCompanyDetailsDataSource
import com.example.finalproject.domain.datasource.company_list.RemoteCompanyListDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteCompanyListDataSource(
        listApiService: CompanyListApiService,
        responseHandler: HandleResponse
    ): RemoteCompanyListDataSource {
        return RemoteCompanyListDataSourceImpl(
            apiService = listApiService,
            responseHandler = responseHandler
        )
    }

    @Provides
    @Singleton
    fun provideRemoteCompanyDetailsDataSource(
        detailsApiService: CompanyDetailsApiService,
        responseHandler: HandleResponse
    ): RemoteCompanyDetailsDataSource {
        return RemoteCompanyDetailsDataSourceImpl(
            apiService = detailsApiService,
            responseHandler = responseHandler
        )
    }

}
package com.example.finalproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.finalproject.data.common.HandleAuthentication
import com.example.finalproject.data.common.HandlePasswordReset
import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.remote.service.stocks_to_watch.StocksToWatchApiService
import com.example.finalproject.data.repository.CreditCardRepositoryImpl
import com.example.finalproject.data.repository.DataStoreRepositoryImpl
import com.example.finalproject.data.repository.LoginRepositoryImpl
import com.example.finalproject.data.repository.RegisterRepositoryImpl
import com.example.finalproject.data.repository.ResetPasswordRepositoryImpl
import com.example.finalproject.data.repository.TransactionsRepositoryImpl
import com.example.finalproject.data.repository.UserFundsRepositoryImpl
import com.example.finalproject.data.repository.UserInitialsRepositoryImpl
import com.example.finalproject.data.repository.company_details.CompanyDetailsRepositoryImpl
import com.example.finalproject.data.repository.company_details_chart.CompanyChartIntradayRepositoryImpl
import com.example.finalproject.data.repository.company_list.CompanyListRepositoryImpl
import com.example.finalproject.data.repository.stocks_to_watch.StocksToWatchRepositoryImpl
import com.example.finalproject.domain.datasource.company_details.RemoteCompanyDetailsDataSource
import com.example.finalproject.domain.datasource.company_details_chart.RemoteCompanyChartIntradayDataSource
import com.example.finalproject.domain.datasource.company_list.RemoteCompanyListDataSource
import com.example.finalproject.domain.repository.CreditCardRepository
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.repository.LoginRepository
import com.example.finalproject.domain.repository.RegisterRepository
import com.example.finalproject.domain.repository.ResetPasswordRepository
import com.example.finalproject.domain.repository.TransactionsRepository
import com.example.finalproject.domain.repository.UserFundsRepository
import com.example.finalproject.domain.repository.UserInitialsRepository
import com.example.finalproject.domain.repository.company_details.CompanyDetailsRepository
import com.example.finalproject.domain.repository.company_details_chart.CompanyChartIntradayRepository
import com.example.finalproject.domain.repository.company_list.CompanyListRepository
import com.example.finalproject.domain.repository.stocks_to_watch.StocksToWatchRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth, handleAuthentication: HandleAuthentication) : LoginRepository {
        return LoginRepositoryImpl(firebaseAuth = firebaseAuth, handleAuthentication = handleAuthentication)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(firebaseAuth: FirebaseAuth, handleAuthentication: HandleAuthentication) : RegisterRepository {
        return RegisterRepositoryImpl(firebaseAuth = firebaseAuth, handleAuthentication = handleAuthentication)
    }

    @Provides
    @Singleton
    fun provideUserDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun providePasswordResetRepository(handlePasswordReset: HandlePasswordReset) : ResetPasswordRepository {
        return ResetPasswordRepositoryImpl(handlePasswordReset = handlePasswordReset)
    }

    @Provides
    @Singleton
    fun provideCompanyListRepository(
        remoteCompanyListDataSource: RemoteCompanyListDataSource,
    ) : CompanyListRepository {
        return CompanyListRepositoryImpl(
            remoteCompanyListDataSource = remoteCompanyListDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideCompanyDetailsRepository(
        remoteCompanyDetailsDataSource: RemoteCompanyDetailsDataSource,
    ) : CompanyDetailsRepository {
        return CompanyDetailsRepositoryImpl(
            remoteCompanyDetailsDataSource = remoteCompanyDetailsDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideStocksToWatchRepository(
        stocksToWatchApiService: StocksToWatchApiService,
        handleResponse: HandleResponse
    ) : StocksToWatchRepository {
        return StocksToWatchRepositoryImpl(
            stocksToWatchApiService = stocksToWatchApiService,
            handleResponse = handleResponse
        )
    }

    @Provides
    @Singleton
    fun provideUserFundsRepository(db : FirebaseFirestore) : UserFundsRepository {
        return UserFundsRepositoryImpl(db = db)
    }

    @Provides
    @Singleton
    fun provideCreditCardRepository(db : FirebaseFirestore) : CreditCardRepository {
        return CreditCardRepositoryImpl(db = db)
    }

    @Provides
    @Singleton
    fun provideTransactionsRepository(db : FirebaseFirestore) : TransactionsRepository {
        return TransactionsRepositoryImpl(db = db)
    }

    @Provides
    @Singleton
    fun provideUserInitialsRepository(db : FirebaseFirestore) : UserInitialsRepository {
        return UserInitialsRepositoryImpl(db = db)
    }

    @Provides
    @Singleton
    fun provideCompanyChartIntradayRepository(
        remoteCompanyChartIntradayDataSource: RemoteCompanyChartIntradayDataSource,
    ) : CompanyChartIntradayRepository {
        return CompanyChartIntradayRepositoryImpl(
            remoteCompanyChartIntradayDataSource = remoteCompanyChartIntradayDataSource,
        )
    }
}
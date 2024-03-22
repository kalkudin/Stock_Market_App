package com.example.finalproject.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.finalproject.data.common.HandleAuthentication
import com.example.finalproject.data.common.HandlePasswordReset
import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.local.dao.stocks.StocksDao
import com.example.finalproject.data.local.dao.user.UserDao
import com.example.finalproject.data.remote.service.company_details.CompanyDetailsApiService
import com.example.finalproject.data.remote.service.company_details_chart.CompanyChartIntradayApiService
import com.example.finalproject.data.remote.service.company_list.CompanyListApiService
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import com.example.finalproject.data.remote.service.stocks_to_watch.StocksToWatchApiService
import com.example.finalproject.data.repository.auth.login.LoginRepositoryImpl
import com.example.finalproject.data.repository.auth.register.RegisterRepositoryImpl
import com.example.finalproject.data.repository.auth.reset_password.ResetPasswordRepositoryImpl
import com.example.finalproject.data.repository.company_details.chart.CompanyChartIntradayRepositoryImpl
import com.example.finalproject.data.repository.company_details.details.CompanyDetailsRepositoryImpl
import com.example.finalproject.data.repository.company_list.CompanyListRepositoryImpl
import com.example.finalproject.data.repository.datastore.DataStoreRepositoryImpl
import com.example.finalproject.data.repository.firestore.credit_card.CreditCardRepositoryImpl
import com.example.finalproject.data.repository.firestore.image_upload.UploadUriRepositoryImpl
import com.example.finalproject.data.repository.firestore.transactions.TransactionsRepositoryImpl
import com.example.finalproject.data.repository.firestore.user_funds.UserFundsRepositoryImpl
import com.example.finalproject.data.repository.firestore.user_initials.UserInitialsRepositoryImpl
import com.example.finalproject.data.repository.stock_news.StockNewsRepositoryImpl
import com.example.finalproject.data.repository.stocks_to_watch.StocksToWatchRepositoryImpl
import com.example.finalproject.data.repository.watchlisted_stocks.WatchlistedStocksRepositoryImpl
import com.example.finalproject.domain.repository.auth.login.LoginRepository
import com.example.finalproject.domain.repository.auth.register.RegisterRepository
import com.example.finalproject.domain.repository.auth.reset_password.ResetPasswordRepository
import com.example.finalproject.domain.repository.company_details.CompanyDetailsRepository
import com.example.finalproject.domain.repository.company_details_chart.CompanyChartIntradayRepository
import com.example.finalproject.domain.repository.company_list.CompanyListRepository
import com.example.finalproject.domain.repository.datastore.DataStoreRepository
import com.example.finalproject.domain.repository.firestore.credit_card.CreditCardRepository
import com.example.finalproject.domain.repository.firestore.image_upload.UploadUriRepository
import com.example.finalproject.domain.repository.firestore.transactions.TransactionsRepository
import com.example.finalproject.domain.repository.firestore.user_funds.UserFundsRepository
import com.example.finalproject.domain.repository.firestore.user_initials.UserInitialsRepository
import com.example.finalproject.domain.repository.stock_news.StockNewsRepository
import com.example.finalproject.domain.repository.stocks_to_watch.StocksToWatchRepository
import com.example.finalproject.domain.repository.watchlisted_stocks.WatchlistedStocksRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        listApiService: CompanyListApiService,
        responseHandler: HandleResponse
    ) : CompanyListRepository {
        return CompanyListRepositoryImpl(
            apiService = listApiService,
            responseHandler = responseHandler
        )
    }

    @Provides
    @Singleton
    fun provideCompanyDetailsRepository(
        detailsApiService: CompanyDetailsApiService,
        responseHandler: HandleResponse
    ) : CompanyDetailsRepository {
        return CompanyDetailsRepositoryImpl(
            apiService = detailsApiService,
            responseHandler = responseHandler
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
        intradayChartApiService: CompanyChartIntradayApiService,
        responseHandler: HandleResponse
    ) : CompanyChartIntradayRepository {
        return CompanyChartIntradayRepositoryImpl(
            apiService = intradayChartApiService,
            responseHandler = responseHandler
        )
    }

    @Provides
    @Singleton
    fun provideStockNewsRepository(
        apiService: StockNewsApiService
    ): StockNewsRepository {
        return StockNewsRepositoryImpl(apiService = apiService)
    }

    @Provides
    @Singleton
    fun provideWatchlistRepository(userDao: UserDao, stocksDao: StocksDao): WatchlistedStocksRepository {
        return WatchlistedStocksRepositoryImpl(
            userDao = userDao,
            stocksDao = stocksDao)
    }

    @Provides
    @Singleton
    fun provideImageRepository(storageReference: StorageReference, @ApplicationContext context: Context) : UploadUriRepository {
        return UploadUriRepositoryImpl(storageReference = storageReference, context = context   )
    }
}
package com.example.finalproject.di

import android.content.Context
import com.example.finalproject.BuildConfig
import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.remote.service.company_details.CompanyDetailsApiService
import com.example.finalproject.data.remote.service.company_details_chart.CompanyChartIntradayApiService
import com.example.finalproject.data.remote.service.company_list.CompanyListApiService
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import com.example.finalproject.data.remote.service.stocks_to_watch.StocksToWatchApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideResponseHandler(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .build()
    }

    @Singleton
    @Provides
    @Named("MOCKYRetrofit")
    fun provideListRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_MOCKY)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).client(provideOkHttpClient()).build()
    }

    @Singleton
    @Provides
    @Named("FMPRetrofit")
    fun provideDetailsRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FMP)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).client(provideOkHttpClient()).build()
    }

    @Singleton
    @Provides
    fun provideCompanyListService(
        @Named("MOCKYRetrofit")
        listRetrofit: Retrofit
    ): CompanyListApiService {
        return listRetrofit.create(CompanyListApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCompanyDetailService(
        @Named("FMPRetrofit")
        detailsRetrofit: Retrofit
    ): CompanyDetailsApiService {
        return detailsRetrofit.create(CompanyDetailsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideStocksToWatchService(
        @Named("MOCKYRetrofit")
        retrofit: Retrofit
    ) : StocksToWatchApiService {
        return retrofit.create(StocksToWatchApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCompanyChartIntradayService(
        @Named("FMPRetrofit")chartRetrofit: Retrofit
    ): CompanyChartIntradayApiService {
        return chartRetrofit.create(CompanyChartIntradayApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideStockNewsService(
        @Named("FMPRetrofit")newsRetrofit: Retrofit
    ): StockNewsApiService {
        return newsRetrofit.create(StockNewsApiService::class.java)
    }
}
package com.example.finalproject.di

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.common.HandleResponse
import com.example.finalproject.data.remote.service.company_details.CompanyDetailsApiService
import com.example.finalproject.data.remote.service.company_details_chart.CompanyChartIntradayApiService
import com.example.finalproject.data.remote.service.company_list.CompanyListApiService
import com.example.finalproject.data.remote.service.stock_news.StockNewsApiService
import com.example.finalproject.domain.repository.stock_news.StockNewsRepository
import com.example.finalproject.domain.usecase.stock_news_usecase.GetStockNewsUseCase
import com.google.firebase.auth.FirebaseAuth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        @Named("MOCKYRetrofit")listRetrofit: Retrofit
    ): CompanyListApiService {
        return listRetrofit.create(CompanyListApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCompanyDetailService(
        @Named("FMPRetrofit")detailsRetrofit: Retrofit
    ): CompanyDetailsApiService {
        return detailsRetrofit.create(CompanyDetailsApiService::class.java)
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
        @Named("FMPRetrofit")newsRetrofit: Retrofit): StockNewsApiService{
        return newsRetrofit.create(StockNewsApiService::class.java)
    }
}
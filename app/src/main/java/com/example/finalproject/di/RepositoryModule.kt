package com.example.finalproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.finalproject.data.common.HandleAuthentication
import com.example.finalproject.data.common.HandlePasswordReset
import com.example.finalproject.data.repository.DataStoreRepositoryImpl
import com.example.finalproject.data.repository.LoginRepositoryImpl
import com.example.finalproject.data.repository.RegisterRepositoryImpl
import com.example.finalproject.data.repository.ResetPasswordRepositoryImpl
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.repository.LoginRepository
import com.example.finalproject.domain.repository.RegisterRepository
import com.example.finalproject.domain.repository.ResetPasswordRepository
import com.google.firebase.auth.FirebaseAuth
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
}
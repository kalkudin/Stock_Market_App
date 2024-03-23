package com.example.finalproject.di

import com.example.finalproject.domain.usecase.user_funds_usecase.AddFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.SubtractFundsUseCase
import com.example.finalproject.domain.usecase.user_funds_usecase.UpdateUserFundsUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

//    @Provides
//    @Singleton
//    fun provideAddFundsUseCase(
//        retrieveUserFundsUseCase: RetrieveUserFundsUseCase,
//        updateUserFundsUseCase: UpdateUserFundsUseCase
//    ): AddFundsUseCase {
//        return AddFundsUseCase(
//            retrieveUserFundsUseCase = retrieveUserFundsUseCase,
//            updateUserFundsUseCase = updateUserFundsUseCase
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideSubstractFundsUseCase(
//        retrieveUserFundsUseCase: RetrieveUserFundsUseCase,
//        updateUserFundsUseCase: UpdateUserFundsUseCase
//    ): SubtractFundsUseCase {
//        return SubtractFundsUseCase(
//            retrieveUserFundsUseCase = retrieveUserFundsUseCase,
//            updateUserFundsUseCase = updateUserFundsUseCase
//        )
//    }

    @Provides
    @Singleton
    fun provideAddFundsUseCase(
        firestore: FirebaseFirestore
    ): AddFundsUseCase {
        return AddFundsUseCase(
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun provideSubstractFundsUseCase(
        firestore: FirebaseFirestore
    ): SubtractFundsUseCase {
        return SubtractFundsUseCase(
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun provideUpdateUserFundsUseCase(
        firestore: FirebaseFirestore
    ): UpdateUserFundsUseCase {
        return UpdateUserFundsUseCase(
            firestore = firestore
        )
    }
}
package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.data.repository.datastore.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(rememberMe: Boolean) {
        dataStoreRepository.saveSession(DataStoreRepositoryImpl.SESSION_KEY, rememberMe)
    }
}
package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.data.repository.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(userUid: String) {
        dataStoreRepository.saveSession(DataStoreRepositoryImpl.SESSION_KEY, userUid)
    }
}
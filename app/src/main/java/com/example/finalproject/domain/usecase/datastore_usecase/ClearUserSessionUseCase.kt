package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearUserSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke() {
        dataStoreRepository.clearSession()
    }
}
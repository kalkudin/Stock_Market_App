package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.data.repository.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readSession(DataStoreRepositoryImpl.SESSION_KEY)
    }
}
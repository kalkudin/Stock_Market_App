package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.data.repository.datastore.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserUidUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readUid(DataStoreRepositoryImpl.UID_KEY)
    }
}
package com.example.finalproject.domain.usecase.datastore_usecase

import android.util.Log
import com.example.finalproject.data.repository.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserSessionUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<Boolean> {
        Log.d("DataStoreUseCase", "UID read")
        return dataStoreRepository.readSession(DataStoreRepositoryImpl.SESSION_KEY)
    }
}
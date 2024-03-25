package com.example.finalproject.domain.usecase.datastore_usecase

import com.example.finalproject.data.repository.datastore.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class SaveUserUidUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(userUid: String) {
        dataStoreRepository.saveUid(DataStoreRepositoryImpl.UID_KEY, userUid)
    }
}
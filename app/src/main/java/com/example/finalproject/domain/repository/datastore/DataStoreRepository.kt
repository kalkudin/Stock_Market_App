package com.example.finalproject.domain.repository.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun readUid(key: Preferences.Key<String>) : Flow<String>
    fun readSession(key: Preferences.Key<Boolean>) : Flow<Boolean>
    suspend fun saveUid(key: Preferences.Key<String>, value: String)
    suspend fun saveSession(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun clearSession()
}
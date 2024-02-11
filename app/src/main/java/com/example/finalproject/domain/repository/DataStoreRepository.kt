package com.example.finalproject.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun readSession(key: Preferences.Key<String>) : Flow<String>
    suspend fun saveSession(key: Preferences.Key<String>, value: String)
    suspend fun clearSession()
}
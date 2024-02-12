package com.example.finalproject.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.finalproject.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore : DataStore<Preferences>
) : DataStoreRepository {

    companion object {
        val UID_KEY = stringPreferencesKey("user_uid_key")
        val SESSION_KEY = booleanPreferencesKey("user_session_key")
    }

    override suspend fun saveUid(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun saveSession(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { it.clear() }
    }

    override fun readUid(key: Preferences.Key<String>): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: ""
        }
    }

    override fun readSession(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }
}
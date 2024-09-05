package org.kamilimu.books.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun saveToDataStore(exampleValue: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY] = exampleValue
        }
    }

    val example: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY] ?: "default_value"
        }

    suspend fun saveBoolToDataStore(exampleValue: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = exampleValue
        }
    }

    val exampleBool: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }

    suspend fun saveIntToDataStore(exampleValue: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY_INT] = exampleValue
        }
    }

    val exampleInt: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY_INT] ?: 0
        }
}

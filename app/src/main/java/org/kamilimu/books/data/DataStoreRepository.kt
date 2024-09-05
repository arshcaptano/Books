package org.kamilimu.books.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    object PreferencesKeys {
        val EXAMPLE_KEY = stringPreferencesKey("example_key")
    }

    suspend fun saveToDataStore(exampleValue: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY] = exampleValue
        }
    }

    val example: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY] ?: "default_value"
        }
}

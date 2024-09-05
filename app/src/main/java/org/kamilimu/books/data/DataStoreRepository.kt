package org.kamilimu.books.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesKeys {
    val EXAMPLE_KEY = stringPreferencesKey("example_key")
    val EXAMPLE_KEY_BOOL = booleanPreferencesKey("example_key_bool")
    val EXAMPLE_KEY_INT = intPreferencesKey("example_key_int")

}

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
            preferences[PreferencesKeys.EXAMPLE_KEY_BOOL] = exampleValue
        }
    }

    val exampleBool: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EXAMPLE_KEY_BOOL] ?: false
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

package org.kamilimu.books.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val EXAMPLE_KEY = stringPreferencesKey("example_key")
    val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
    val EXAMPLE_KEY_INT = intPreferencesKey("example_key_int")
}
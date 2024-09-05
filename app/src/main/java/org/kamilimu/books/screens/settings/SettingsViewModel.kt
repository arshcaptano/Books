package org.kamilimu.books.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kamilimu.books.data.DataStoreRepository

open class SettingsViewModel(private val repository: DataStoreRepository) : ViewModel() {
    private val _screenState = MutableStateFlow(SettingsScreenState())
    val screenState: StateFlow<SettingsScreenState> = _screenState.asStateFlow()

    private fun observeBooks() {
        viewModelScope.launch {
            repository.example.collect { value ->
                _screenState.value = _screenState.value.copy(example = value)
            }
        }
    }
}
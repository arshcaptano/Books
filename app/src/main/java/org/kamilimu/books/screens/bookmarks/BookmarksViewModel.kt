package org.kamilimu.books.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kamilimu.books.database.AppDatabase
import org.kamilimu.books.screens.bookmarks.db.BookEntity

class BookmarksViewModel(private val database: AppDatabase) : ViewModel() {
    private val _screenState = MutableStateFlow(BookmarksScreenState())
    val screenState: StateFlow<BookmarksScreenState> = _screenState.asStateFlow()

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        _screenState.value = _screenState.value.copy(isLoading = true)

        viewModelScope.launch {
            database.getBookDao().getBooks()
        }
    }
}


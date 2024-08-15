package org.kamilimu.books.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kamilimu.books.database.AppDatabase
import org.kamilimu.books.screens.bookmarks.db.BookEntity

class BookmarksViewModel(private val appDatabase: AppDatabase) : ViewModel() {
    private val _screenState = MutableStateFlow(BookmarksScreenState())
    val screenState: StateFlow<BookmarksScreenState> = _screenState.asStateFlow()

    init {
        insertDemoBooks()
        getBookmarks()
    }

     fun insertDemoBooks(){
         viewModelScope.launch {
             appDatabase.getBookDao().insert(
                 listOf(
                     BookEntity(id = 1, title = "Book C"),
                     BookEntity(id = 2, title = "Book D")
                 )
             )
         }
    }

    private fun getBookmarks() {
        _screenState.value = _screenState.value.copy(isLoading = true)

        viewModelScope.launch {
            appDatabase.getBookDao()
        }
    }
}


package org.kamilimu.books.screens.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kamilimu.books.database.AppDatabase
import org.kamilimu.books.screens.bookmarks.db.BookEntity
import org.kamilimu.books.screens.books.models.Book

class BooksViewModel(
    private val repository: BooksRepository,
    private val database: AppDatabase
) : ViewModel() {
    private val _screenState = MutableStateFlow(BooksScreenState())
    val screenState: StateFlow<BooksScreenState> = _screenState.asStateFlow()

    init {
        getBooks()
    }

    private fun getBooks() {
        _screenState.value = _screenState.value.copy(isLoading = true)

        viewModelScope.launch {
            repository.getBooks(
                onSuccess = {
                    _screenState.value = _screenState.value.copy(isLoading = false)

                    _screenState.value = _screenState.value.copy(books = it)

                    val list = it.map { book -> BookEntity(id = book.id, title = book.title) }

                    viewModelScope.launch {
                        database.getBookDao().insert(list)
                    }
                },
                onFail = {
                    _screenState.value = _screenState.value.copy(isLoading = false)

                    _screenState.value = _screenState.value.copy(errorMessage = it)
                }
            )
        }
    }
}

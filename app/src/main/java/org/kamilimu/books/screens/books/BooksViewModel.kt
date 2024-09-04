package org.kamilimu.books.screens.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kamilimu.books.database.AppDatabase
import org.kamilimu.books.screens.books.db.BookEntity

class BooksViewModel(
    private val repository: BooksRepository,
    private val database: AppDatabase
) : ViewModel() {
    private val _screenState = MutableStateFlow(BooksScreenState())
    val screenState: StateFlow<BooksScreenState> = _screenState.asStateFlow()

    init {
        fetchBooks()

        observeBooks()
    }

    private fun fetchBooks() {
        _screenState.value = _screenState.value.copy(isLoading = true)

        viewModelScope.launch {
            repository.getBooks(
                onSuccess = {
                    it.map { book ->
                        viewModelScope.launch {
                            database.getBookDao().insert(
                                BookEntity(
                                    id = book.id,
                                    title = book.title,
                                    authors = book.authors,
                                    subjects = book.subjects
                                )
                            )
                        }
                    }

                    _screenState.value = _screenState.value.copy(isLoading = false)
                },
                onFail = {
                    _screenState.value = _screenState.value.copy(isLoading = false)

                    _screenState.value = _screenState.value.copy(errorMessage = it)
                }
            )
        }
    }

    private fun observeBooks() {
        viewModelScope.launch {
            database.getBookDao().observeBooks().collect { bookEntities ->
                _screenState.value = _screenState.value.copy(books = bookEntities)
            }
        }
    }

    fun saveBook(bookId: Int) {
        viewModelScope.launch {
            database.getBookDao().saveBook(bookId)
        }
    }
}

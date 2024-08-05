package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
): ViewModel() {
    private val _booksState = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val booksState: StateFlow<BooksViewState> = _booksState.asStateFlow()

    init {
        getAllBooks()
    }

    private fun getAllBooks() = viewModelScope.launch {
        bookRepository.getBooks()
            .onRight { apiResponse ->
                _booksState.update {
                    BooksViewState.Success(apiResponse.results)
                }
            }
            .onLeft { networkError ->
                _booksState.update {
                    BooksViewState.Failure(networkError.error.message)
                }
            }
    }
}
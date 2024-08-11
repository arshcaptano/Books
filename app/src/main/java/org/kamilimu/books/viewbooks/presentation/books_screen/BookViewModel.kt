package org.kamilimu.books.viewbooks.presentation.books_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kamilimu.books.viewbooks.domain.mapper.toBook
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
): ViewModel() {
    private val _booksState = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val booksState: StateFlow<BooksViewState> = _booksState.asStateFlow()

    private val _bookmarkedBooks = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val bookmarkedBooks: StateFlow<BooksViewState> = _bookmarkedBooks.asStateFlow()

    init {
        getAllBooks()
        getBookmarkedBooks()
    }

    private fun getAllBooks() = viewModelScope.launch {
        bookRepository.getBooks()
            .onRight { apiResponse ->
                _booksState.update {
                    BooksViewState.Success(
                        apiResponse.results.map { bookRemote -> bookRemote.toBook() }
                    )
                }
            }
            .onLeft { networkError ->
                _booksState.update {
                    BooksViewState.Failure(networkError.error.message)
                }
            }
    }

    fun onFavouriteClicked(book: Book) = viewModelScope.launch {
        if (book.isBookmarked) {
            bookRepository.deleteBookmarkedBookById(book.id)
            updateBookState(book.copy(isBookmarked = false))
        } else {
            bookRepository.insertBook(book)
            updateBookState(book.copy(isBookmarked = true))
        }
    }

    private fun updateBookState(updatedBook: Book) {
        _booksState.update { currentState ->
            if (currentState is BooksViewState.Success) {
                BooksViewState.Success(
                    currentState.books.map { book ->
                        if (book.id == updatedBook.id) updatedBook else book
                    }
                )
            } else {
                currentState
            }
        }
    }

    private fun getBookmarkedBooks() = viewModelScope.launch {
        bookRepository.getBookmarkedBooks().collect { books ->
            if (books.isEmpty()) {
                _bookmarkedBooks.update {
                    BooksViewState.Failure("No bookmarks added")
                }
            } else {
                _bookmarkedBooks.update {
                    BooksViewState.Success(books)
                }
            }
        }
    }
}
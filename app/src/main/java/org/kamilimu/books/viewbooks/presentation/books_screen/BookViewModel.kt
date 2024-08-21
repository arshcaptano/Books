package org.kamilimu.books.viewbooks.presentation.books_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kamilimu.books.bookmarks.domain.repository.BookmarkRepository
import org.kamilimu.books.viewbooks.domain.mapper.toBook
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val _booksState = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val booksState: StateFlow<BooksViewState> = _booksState.asStateFlow()

    init {
        getAllBooks()
    }

    /**
     * Fetches data from the API at the `/books` endpoint, and updates the state
     */
    private fun getAllBooks() = viewModelScope.launch {
        /**
         * Create a set of `bookId`s of books in the local database
         */
        val bookmarkedIds = bookmarkRepository.getAllBookmarks()
            .map { bookmarkedBooks ->
                bookmarkedBooks.map { it.id }.toSet()
            }
            .first()  // Get the first element emitted by the flow

        bookRepository.getBooks()
            .onRight { apiResponse ->
                /**
                 * Sync the bookmarked books by updating their `isBookmarked` field where
                 * the `bookId` appears in the local database
                 */
                val syncedBooks = apiResponse.results.map { bookRemote ->
                    val book = bookRemote.toBook()
                    book.copy(isBookmarked = bookmarkedIds.contains(book.id))
                }
                _booksState.update {
                    BooksViewState.Success(syncedBooks)  // Update the state with the synced books
                }
            }
            .onLeft { networkError ->
                _booksState.update {
                    BooksViewState.Failure(networkError.error.message)
                }
            }
    }

    /**
     * Adds or deletes a `book` from the bookmarks in the local database
     * If `isBookmarked` is true when the IconButton is clicked, the book is
     * deleted from the database, otherwise it is added to the database
     */
    fun onFavouriteClicked(book: Book) = viewModelScope.launch {
        if (book.isBookmarked) {
            bookmarkRepository.deleteBookmark(book)
            updateBookState(book.copy(isBookmarked = false))
        } else {
            bookmarkRepository.addNewBookmark(book)
            updateBookState(book.copy(isBookmarked = true))
        }
    }

    /**
     * Updates the state of the `List<Book>` in `BooksViewState.Success`
     * when the favourite icon button is clicked
     */
    private fun updateBookState(updatedBook: Book) = viewModelScope.launch {
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

    /**
     * Syncs the state of bookmarked books when [BooksHomeScreen] is in focus
     */
    fun syncBookmarkedBooksInBookHomeScreen() = viewModelScope.launch {
        Log.d("Sync_Home_Page", "Syncing function called")

        val bookmarkedIds = withContext(Dispatchers.IO) {
            bookmarkRepository.getAllBookmarks()
                .map { bookmarkedBooks ->
                    bookmarkedBooks.map { it.id }.toSet()
                }
                .first()  // Only the first flow emitted
        }

        _booksState.update { currentState ->
            if (currentState is BooksViewState.Success) {
                BooksViewState.Success(
                    currentState.books.map { book ->
                        book.copy(isBookmarked = bookmarkedIds.contains(book.id))
                    }
                )
            } else {
                currentState
            }
        }
    }
}
package org.kamilimu.books.viewbooks.presentation.books_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val bookmarkRepository: BookmarkRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _booksState = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val booksState: StateFlow<BooksViewState> = _booksState.asStateFlow()

    private val _bookDetails = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val bookDetails: StateFlow<BooksViewState> = _bookDetails.asStateFlow()

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
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Book removed from your Bookmarks",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            bookmarkRepository.addNewBookmark(book)
            updateBookState(book.copy(isBookmarked = true))
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "Book added to your Bookmarks",
                    Toast.LENGTH_SHORT
                ).show()
            }
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

    fun onBookCardClicked(bookId: Int) = viewModelScope.launch {
        _bookDetails.update { BooksViewState.Loading }
        bookRepository.getBookById(bookId)
            .onRight { bookRemote ->
                val bookmark: Book? = try {
                    bookmarkRepository.getBookmarkById(bookId)
                } catch (e: Exception) {
                    null
                }

                _bookDetails.update {
                    BooksViewState.Success(
                        listOf(
                            if (bookmark == null)
                                bookRemote.toBook()
                            else
                                bookRemote.toBook().copy(isBookmarked = true)
                        )
                    )
                }
            }
            .onLeft { networkError ->
                _bookDetails.update {
                    BooksViewState.Failure(networkError.error.message)
                }
            }
    }

    fun updateBookInBookDetailsScreen(book: Book) = viewModelScope.launch {
        val updatedBook = book.copy(isBookmarked = !book.isBookmarked)
        _bookDetails.update { currentState ->
            if (currentState is BooksViewState.Success) {
                BooksViewState.Success(listOf(updatedBook))
            } else {
                currentState
            }
        }
    }
}
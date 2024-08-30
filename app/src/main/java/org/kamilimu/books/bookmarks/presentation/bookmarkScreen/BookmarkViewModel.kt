package org.kamilimu.books.bookmarks.presentation.bookmarkScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kamilimu.books.bookmarks.domain.repository.BookmarkRepository
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.presentation.books_screen.BooksViewState
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val _bookmarkState = MutableStateFlow<BooksViewState>(BooksViewState.Loading)
    val bookmarkState: StateFlow<BooksViewState> = _bookmarkState.asStateFlow()

    init {
        getAllBookmarks()
    }

    /**
     * Query for all existing bookmarks in the local database
     */
    private fun getAllBookmarks() = viewModelScope.launch {
        bookmarkRepository.getAllBookmarks().collect { books ->
            if (books.isEmpty()) {
                _bookmarkState.update {
                    BooksViewState.Failure(message = "No bookmarks added")
                }
            } else {
                _bookmarkState.update {
                    BooksViewState.Success(books)
                }
            }
        }
    }

    /**
     * Adds a new book to bookmarks in the local database
     */
    fun onAddBookmark(book: Book) = viewModelScope.launch {
        bookmarkRepository.addNewBookmark(book)
        getAllBookmarks()  // Update the state after performing a Create0i8
    }

    /**
     * Deletes a bookmark from the local database
     */
    fun onDeleteBookmark(book: Book) = viewModelScope.launch {
        bookmarkRepository.deleteBookmark(book)
        getAllBookmarks()  // Update the state after performing a Delete
    }

    /**
     * Retrieves a specific book from the bookmarks stored in the local database
     */
    suspend fun getBookmarkById(bookId: Int): Book {
        return withContext(Dispatchers.IO) {
            bookmarkRepository.getBookmarkById(bookId)
        }
    }
}
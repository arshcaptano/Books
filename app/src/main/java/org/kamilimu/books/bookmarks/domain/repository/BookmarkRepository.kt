package org.kamilimu.books.bookmarks.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kamilimu.books.viewbooks.domain.model.Book

interface BookmarkRepository {
    /**
     * Creates a new record in the database table
     */
    suspend fun addNewBookmark(book: Book)

    /**
     * Deletes an existing record from the database table
     * @param book [Book] object to be deleted
     */
    suspend fun deleteBookmark(book: Book)

    /**
     * Queries for all records in the database table
     */
    fun getAllBookmarks(): Flow<List<Book>>

    /**
     * Queries for a particular [Book] in the database table
     * @param bookId Unique identifier of the book; obtained from the API response
     */
    suspend fun getBookmarkById(bookId: Int): Book
}
package org.kamilimu.books.bookmarks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.kamilimu.books.bookmarks.domain.model.BookEntity

@Dao
interface BookmarkDao {
    /**
     * Creates a new record in the `books` table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewBookmark(book: BookEntity)

    /**
     * Deletes an existing record from `books` table
     */
    @Delete
    suspend fun deleteBookmark(book: BookEntity)

    /**
     * Queries all existing records in `books` table
     */
    @Query("SELECT * FROM books")
    fun getAllBookmarks(): Flow<List<BookEntity>>

    /**
     * Queries for a particular book in the `books` table
     * @param bookId Unique identifier of the book; obtained from the API response
     */
    @Query("SELECT * FROM books WHERE bookId = :bookId")
    suspend fun getBookmarkById(bookId: Int): BookEntity
}
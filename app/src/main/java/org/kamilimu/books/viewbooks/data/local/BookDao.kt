package org.kamilimu.books.viewbooks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getBookmarkedBooks(): Flow<List<BookEntity>>

    @Query("DELETE FROM books WHERE bookId = :bookId")
    suspend fun deleteBookmarkedBookById(bookId: Int)

    @Query("SELECT * FROM books WHERE bookId = :bookId")
    suspend fun getBookmarkedBookById(bookId: Int): BookEntity?
}
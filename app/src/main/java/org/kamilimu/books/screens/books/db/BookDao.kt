package org.kamilimu.books.screens.books.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.kamilimu.books.database.BaseDao
import org.kamilimu.books.screens.books.models.Book


@Dao
interface BookDao : BaseDao<BookEntity> {
    @Query("SELECT * FROM books")
    fun observeBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    fun observeBookById(bookId: Int): Flow<BookEntity?>

    @Query("UPDATE books SET isSaved = :save WHERE id = :bookId")
    suspend fun saveBook(bookId: Int, save: Boolean)

    @Query("DELETE FROM books")
    suspend fun deleteAll()
}
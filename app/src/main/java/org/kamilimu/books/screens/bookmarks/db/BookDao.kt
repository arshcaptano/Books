package org.kamilimu.books.screens.bookmarks.db

import androidx.room.Dao
import androidx.room.Query
import org.kamilimu.books.database.BaseDao


@Dao
interface BookDao : BaseDao<BookEntity> {
    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<BookEntity>

    @Query("DELETE FROM books")
    suspend fun deleteAll()
}
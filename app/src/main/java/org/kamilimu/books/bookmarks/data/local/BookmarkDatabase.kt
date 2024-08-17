package org.kamilimu.books.bookmarks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.kamilimu.books.bookmarks.domain.model.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun getBookmarkDao(): BookmarkDao
}
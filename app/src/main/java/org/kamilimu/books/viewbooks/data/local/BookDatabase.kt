package org.kamilimu.books.viewbooks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Contains the database holder and serves as the main access point for the
 * underlying connection to your app's persisted, relational data.
 */
@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookDatabase: RoomDatabase() {
    abstract fun getBookDao(): BookDao
}
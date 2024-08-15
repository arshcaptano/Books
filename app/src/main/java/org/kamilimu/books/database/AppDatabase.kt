package org.kamilimu.books.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kamilimu.books.screens.bookmarks.db.BookDao
import org.kamilimu.books.screens.bookmarks.db.BookEntity

const val DbName = "books_db"

@Database(
    entities = [
        BookEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        /**
         * This function is callback function after database is created
         * It will be executed only at the time after database is created
         * [database] is an instance for AppDatabase*/
        fun onCreate(scope: CoroutineScope, database: AppDatabase?) {
            val list = listOf(
                BookEntity(id = 1, title = "Book A"),
                BookEntity(id = 2, title = "Book B")
            )
            scope.launch {
                database?.getBookDao()?.insert(list)
            }
        }
    }

    abstract fun getBookDao(): BookDao

    fun clearDatabase() {
        clearAllTables()
    }
}

/**
 * Migration(1, 2) means from database version 1 to 2*/
val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // We can perform actions like
        /*database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")*/
    }
}


fun provideDatabase(context: Context, scope: CoroutineScope): AppDatabase {
    var database: AppDatabase? = null

    database = Room.databaseBuilder(context, AppDatabase::class.java, DbName)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                AppDatabase.onCreate(scope = scope, database = database)
            }
        })
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()
    return database
}

fun provideBookDao(appDatabase: AppDatabase): BookDao {
    return appDatabase.getBookDao()
}
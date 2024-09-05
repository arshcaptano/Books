package org.kamilimu.books.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kamilimu.books.screens.books.db.BookDao
import org.kamilimu.books.screens.books.db.BookEntity

const val DbName = "books_db"

@Database(
    entities = [
        BookEntity::class,
    ],
    version = 4,
    exportSchema = true,
//    autoMigrations = [
//        AutoMigration(
//            from = 4,
//            to = 5,
//            spec = BookAutoMigrationSpec::class
//        )
//    ]
)
@TypeConverters(Converters::class)
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
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Step 1: Add the new column with a default value of `false` (0 in SQLite)
        db.execSQL("ALTER TABLE books ADD COLUMN isSaved INTEGER NOT NULL DEFAULT 0")
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

class BookAutoMigrationSpec : AutoMigrationSpec {
    override fun onPostMigrate(db: SupportSQLiteDatabase) {
        // Optional: custom logic that runs after the migration
        db.execSQL("ALTER TABLE books DELETE COLUMN test INTEGER NOT NULL DEFAULT 0")
    }
}
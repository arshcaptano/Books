package org.kamilimu.books.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
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
    version = 6,
    exportSchema = true,
//    autoMigrations = [
//        AutoMigration(
//            from = 5,
//            to = 6,
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
            // Setup your database
        }
    }

    abstract fun getBookDao(): BookDao

    fun clearDatabase() {
        clearAllTables()
    }
}

val MIGRATION_0_1 = object : Migration(0, 1) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE books ADD COLUMN isSaved INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE books ADD COLUMN age INTEGER NOT NULL DEFAULT 3")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE books ADD COLUMN year INTEGER NOT NULL DEFAULT 3")
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
        .fallbackToDestructiveMigration()
        .build()
    return database
}

fun provideBookDao(appDatabase: AppDatabase): BookDao {
    return appDatabase.getBookDao()
}

@DeleteColumn.Entries(
    DeleteColumn(
        tableName = "books",
        columnName = "age"
    ),
    DeleteColumn(
        tableName = "books",
        columnName = "year"
    )
)
class BookAutoMigrationSpec : AutoMigrationSpec {
    override fun onPostMigrate(db: SupportSQLiteDatabase) {
        // Optional: custom logic that runs after the migration
        db.execSQL("ALTER TABLE books DROP COLUMN test")
        db.execSQL("ALTER TABLE books ADD COLUMN test INTEGER DEFAULT 7")
    }
}
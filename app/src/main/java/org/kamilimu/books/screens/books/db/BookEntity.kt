package org.kamilimu.books.screens.books.db

import android.view.translation.Translator
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.kamilimu.books.screens.books.models.Author

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val title: String = "",
    val isSaved: Boolean = false,
    val authors: List<Author> = emptyList(),
    val subjects: List<String> = emptyList(),
    val test: Int? = 0
)

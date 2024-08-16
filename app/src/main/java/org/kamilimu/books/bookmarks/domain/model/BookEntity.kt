package org.kamilimu.books.bookmarks.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val bookId: Int,
    val title: String,
    val subjects: List<String>,
    val authors: String,  // JSON string containing a List<Person>
    val translators: String,  // JSON string containing a List<Person>
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean?,
    val mediaType: String,
    val formats: String,  // JSON string containing a Format object
    val downloadCount: Int
)

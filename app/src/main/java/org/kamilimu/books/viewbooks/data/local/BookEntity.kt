package org.kamilimu.books.viewbooks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    val bookId: Int,
    val title: String,
    val subjects: List<String>,
    val authors: String,  // JSON string representing a List<Person>
    val translators: String,  // JSON string representing a List<Person>
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean,
    val mediaType: String,
    val image: String,
    val downloadCount: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0
}

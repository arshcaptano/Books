package org.kamilimu.books.viewbooks.domain.model

data class Book(
    val id: Int,
    val title: String,
    val subjects: List<String>,
    val authors: List<Person>,
    val translators: List<Person>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean?,
    val mediaType: String,
    val formats: Format,
    val downloadCount: Int,
    var isBookmarked: Boolean = false
)

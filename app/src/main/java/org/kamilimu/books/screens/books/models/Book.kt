package org.kamilimu.books.screens.books.models

import android.view.translation.Translator

data class Book(
    val id: Int = 0,
    val title: String = "",
    val authors: List<Author> = emptyList(),
    val translators: List<Translator> = emptyList(),
    val subjects: List<String> = emptyList(),
    val bookshelves: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val copyright: Boolean = false,
    val mediaType: String = "",
    val downloadCount: Int = 0
)
package org.kamilimu.books.screens.books.models

import android.view.translation.Translator

data class Book(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val translators: List<Translator>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean,
    val mediaType: String,
    val downloadCount: Int
)
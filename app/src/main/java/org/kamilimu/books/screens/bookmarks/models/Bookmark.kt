package org.kamilimu.books.screens.bookmarks.models

import org.kamilimu.books.screens.books.models.Book

data class Bookmark(
    val bookmarkId: Int,
    val book: Book
)

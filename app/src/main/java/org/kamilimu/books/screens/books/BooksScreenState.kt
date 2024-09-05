package org.kamilimu.books.screens.books

import org.kamilimu.books.screens.books.db.BookEntity

data class BooksScreenState(
    var isLoading: Boolean = true,
    var books: List<BookEntity> = emptyList(),
    var errorMessage: String = ""
)
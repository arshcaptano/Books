package org.kamilimu.books.screens.books

import org.kamilimu.books.screens.books.models.Book

data class BooksScreenState(
    var isLoading: Boolean = true,
    var books: List<Book> = emptyList(),
    var errorMessage: String = ""
)
package org.kamilimu.books.viewbooks.presentation.books_screen

import org.kamilimu.books.viewbooks.domain.model.Book

sealed interface BooksViewState {
    data class Success(val books: List<Book>): BooksViewState
    data class Failure(val message: String): BooksViewState
    object Loading: BooksViewState
}
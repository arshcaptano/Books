package org.kamilimu.books.viewbooks.domain.repository

import arrow.core.Either
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.BookData
import org.kamilimu.books.viewbooks.domain.model.NetworkError

interface BookRepository {
    suspend fun getBooks(): Either<NetworkError, BookData>

    suspend fun getBookById(bookId: Int): Either<NetworkError, Book>
}
package org.kamilimu.books.viewbooks.domain.repository

import arrow.core.Either
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.NetworkError

interface BookRepository {
    suspend fun getBooks(): Either<NetworkError, List<Book>>

    suspend fun getBookById(bookId: Int): Either<NetworkError, Book>
}
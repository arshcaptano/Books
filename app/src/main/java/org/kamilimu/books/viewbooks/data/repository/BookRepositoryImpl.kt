package org.kamilimu.books.viewbooks.data.repository

import arrow.core.Either
import org.kamilimu.books.viewbooks.data.mapper.toNetworkError
import org.kamilimu.books.viewbooks.data.remote.BookApiService
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.NetworkError
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(): Either<NetworkError, List<Book>> {
        return Either.catch {
            bookApiService.getBooks()
        }.mapLeft { throwable ->
            throwable.toNetworkError()
        }
    }

    override suspend fun getBookById(bookId: Int): Either<NetworkError, Book> {
        return Either.catch {
            bookApiService.getBookById(bookId)
        }.mapLeft { throwable ->
            throwable.toNetworkError()
        }
    }
}
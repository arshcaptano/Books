package org.kamilimu.books.viewbooks.data.repository

import arrow.core.Either
import org.kamilimu.books.viewbooks.data.mapper.toNetworkError
import org.kamilimu.books.viewbooks.data.remote.BookApiService
import org.kamilimu.books.viewbooks.domain.model.BookData
import org.kamilimu.books.viewbooks.domain.model.BookRemote
import org.kamilimu.books.viewbooks.domain.model.NetworkError
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApiService: BookApiService
): BookRepository {
    override suspend fun getBooks(): Either<NetworkError, BookData> {
        return Either.catch {
            bookApiService.getBooks()
        }.mapLeft { throwable ->
            throwable.toNetworkError()
        }
    }

    override suspend fun getBookById(bookId: Int): Either<NetworkError, BookRemote> {
        return Either.catch {
            bookApiService.getBookById(bookId)
        }.mapLeft { throwable ->
            throwable.toNetworkError()
        }
    }
}
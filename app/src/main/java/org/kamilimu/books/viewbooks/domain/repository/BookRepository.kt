package org.kamilimu.books.viewbooks.domain.repository

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.BookRemote
import org.kamilimu.books.viewbooks.domain.model.BookData
import org.kamilimu.books.viewbooks.domain.model.NetworkError

interface BookRepository {
    suspend fun getBooks(): Either<NetworkError, BookData>

    suspend fun getBookById(bookId: Int): Either<NetworkError, BookRemote>

    suspend fun insertBook(book: Book)

    suspend fun getBookmarkedBookById(bookId: Int): Book?

    fun getBookmarkedBooks(): Flow<List<Book>>

    suspend fun deleteBookmarkedBookById(bookId: Int)
}
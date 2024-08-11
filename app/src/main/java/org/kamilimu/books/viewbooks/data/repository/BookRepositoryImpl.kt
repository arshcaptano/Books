package org.kamilimu.books.viewbooks.data.repository

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kamilimu.books.viewbooks.data.local.BookDao
import org.kamilimu.books.viewbooks.data.mapper.toBook
import org.kamilimu.books.viewbooks.data.mapper.toBookEntity
import org.kamilimu.books.viewbooks.data.mapper.toNetworkError
import org.kamilimu.books.viewbooks.data.remote.BookApiService
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.BookRemote
import org.kamilimu.books.viewbooks.domain.model.BookData
import org.kamilimu.books.viewbooks.domain.model.NetworkError
import org.kamilimu.books.viewbooks.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApiService: BookApiService,
    private val bookDao: BookDao
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

    override suspend fun insertBook(book: Book) {
        return bookDao.insertBook(book.toBookEntity())
    }

    override fun getBookmarkedBooks(): Flow<List<Book>> {
        return bookDao.getBookmarkedBooks().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override suspend fun getBookmarkedBookById(bookId: Int): Book {
         return bookDao.getBookmarkedBookById(bookId)!!.toBook()
    }

    override suspend fun deleteBookmarkedBookById(bookId: Int) {
        return bookDao.deleteBookmarkedBookById(bookId)
    }
}
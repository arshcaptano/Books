package org.kamilimu.books.bookmarks.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kamilimu.books.bookmarks.data.local.BookmarkDao
import org.kamilimu.books.bookmarks.domain.mapper.toBook
import org.kamilimu.books.bookmarks.domain.mapper.toBookEntity
import org.kamilimu.books.bookmarks.domain.repository.BookmarkRepository
import org.kamilimu.books.viewbooks.domain.model.Book
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun addNewBookmark(book: Book) {
        bookmarkDao.addNewBookmark(book.toBookEntity())
    }

    override suspend fun deleteBookmark(book: Book) {
        bookmarkDao.deleteBookmark(book.toBookEntity())
    }

    override fun getAllBookmarks(): Flow<List<Book>> {
        return bookmarkDao.getAllBookmarks().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    override suspend fun getBookmarkById(bookId: Int): Book {
        return bookmarkDao.getBookmarkById(bookId).toBook()
    }
}
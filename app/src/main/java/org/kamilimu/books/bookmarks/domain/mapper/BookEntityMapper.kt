package org.kamilimu.books.bookmarks.domain.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.kamilimu.books.bookmarks.domain.model.BookEntity
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.Format
import org.kamilimu.books.viewbooks.domain.model.Person

private val gson = Gson()
private val personListType = object: TypeToken<List<Person>>() {}.type
private val formatType = object: TypeToken<Format>() {}.type

/**
 * Maps a [BookEntity] to a [Book]
 */
fun BookEntity.toBook(): Book {
    return Book(
        id = this.bookId,
        title = this.title,
        subjects = this.subjects,
        authors = gson.fromJson(this.authors, personListType),
        translators = gson.fromJson(this.translators, personListType),
        bookshelves = this.bookshelves,
        languages = this.languages,
        copyright = this.copyright,
        mediaType = this.mediaType,
        formats = gson.fromJson(this.formats, formatType),
        downloadCount = this.downloadCount,
        isBookmarked = true
    )
}


/**
 * Maps a [Book] to a [BookEntity]
 */
fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        bookId = this.id,
        title = this.title,
        subjects = this.subjects,
        authors = gson.toJson(this.authors),
        translators = gson.toJson(this.translators),
        bookshelves = this.bookshelves,
        languages = this.languages,
        copyright = this.copyright,
        mediaType = this.mediaType,
        formats = gson.toJson(this.formats),
        downloadCount = this.downloadCount
    )
}
package org.kamilimu.books.viewbooks.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.kamilimu.books.viewbooks.data.local.BookEntity
import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.Format
import org.kamilimu.books.viewbooks.domain.model.Person

private val gson = Gson()
private val personListType = object: TypeToken<List<Person>>() {}.type


/**
 * Maps a [Book] to a [BookEntity] for persistence in the Room database
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
        copyright = this.copyright ?: false,
        mediaType = this.mediaType,
        image = this.formats.jpeg,
        downloadCount = this.downloadCount
    )
}


/**
 * Maps a [BookEntity] to a [Book] for presentation
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
        formats = Format("", "", "", "", this.image, "", ""),
        downloadCount = this.downloadCount,
        isBookmarked = true
    )
}
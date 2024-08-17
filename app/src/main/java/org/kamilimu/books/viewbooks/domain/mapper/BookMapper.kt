package org.kamilimu.books.viewbooks.domain.mapper

import org.kamilimu.books.viewbooks.domain.model.Book
import org.kamilimu.books.viewbooks.domain.model.BookRemote

/**
 * Maps a [BookRemote] object to a domain layer [Book] object
 */
fun BookRemote.toBook(): Book {
    return Book(
        id = this.id,
        title = this.title,
        subjects = this.subjects,
        authors = this.authors,
        translators = this.translators,
        bookshelves = this.bookshelves,
        languages = this.languages,
        copyright = this.copyright,
        mediaType = this.mediaType,
        formats = this.formats,
        downloadCount = this.downloadCount
    )
}
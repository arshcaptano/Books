package org.kamilimu.books.viewbooks.domain.model

/**
 * Used to map the JSON response from GET /books endpoint to a data class
 */
data class BookData(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<BookRemote>
)
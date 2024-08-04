package org.kamilimu.books.viewbooks.domain.model

data class Result(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Book>
)
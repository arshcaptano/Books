package org.kamilimu.books.screens.books.models

import com.google.gson.annotations.SerializedName

data class BooksResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    @SerializedName("results")
    val books: List<Book> = listOf()
)
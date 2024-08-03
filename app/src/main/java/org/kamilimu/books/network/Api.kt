package org.kamilimu.books.network

import androidx.annotation.Keep
import org.kamilimu.books.baseUrl
import org.kamilimu.books.screens.books.models.BooksResponse
import retrofit2.Response
import retrofit2.http.GET


@Keep
interface Api {
    @GET("/books")
    suspend fun getBooks(): BooksResponse
}
package org.kamilimu.books.viewbooks.data.remote

import org.kamilimu.books.viewbooks.domain.model.BookRemote
import org.kamilimu.books.viewbooks.domain.model.BookData
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("/books")
    suspend fun getBooks(): BookData

    @GET("/books/{id}")
    suspend fun getBookById(@Path("id") id: Int): BookRemote
}
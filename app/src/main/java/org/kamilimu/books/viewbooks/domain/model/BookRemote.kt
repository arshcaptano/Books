package org.kamilimu.books.viewbooks.domain.model

import com.google.gson.annotations.SerializedName

data class BookRemote(
    val id: Int,
    val title: String,
    val subjects: List<String>,
    val authors: List<Person>,
    val translators: List<Person>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean?,
    @SerializedName("media_type")
    val mediaType: String,
    val formats: Format,
    @SerializedName("download_count")
    val downloadCount: Int
)

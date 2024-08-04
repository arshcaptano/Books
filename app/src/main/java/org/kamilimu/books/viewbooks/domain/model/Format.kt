package org.kamilimu.books.viewbooks.domain.model

import com.google.gson.annotations.SerializedName

data class Format(
    @SerializedName("application/epub+zip")
    val zip: String,
    @SerializedName("application/octet-stream")
    val octetStream: String,
    @SerializedName("application/rdf+xml")
    val xml: String,
    @SerializedName("application/x-mobipocket-ebook")
    val ebook: String,
    @SerializedName("image/jpeg")
    val jpeg: String,
    @SerializedName("text/html")
    val html: String,
    @SerializedName("text/plain; charset=us-ascii")
    val plain: String
)
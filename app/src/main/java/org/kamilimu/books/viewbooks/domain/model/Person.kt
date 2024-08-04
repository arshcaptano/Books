package org.kamilimu.books.viewbooks.domain.model

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("death_year")
    val deathYear: Int?,
    val name: String
)

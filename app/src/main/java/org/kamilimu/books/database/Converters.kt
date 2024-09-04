package org.kamilimu.books.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.kamilimu.books.screens.books.models.Author

class Converters {

    @TypeConverter
    fun fromAuthorsList(authors: List<Author>?): String? {
        return Gson().toJson(authors)
    }

    @TypeConverter
    fun toAuthorsList(authorsString: String?): List<Author>? {
        val type = object : TypeToken<List<Author>>() {}.type
        return Gson().fromJson(authorsString, type)
    }

    @TypeConverter
    fun fromSubjectsList(subjects: List<String>?): String? {
        return Gson().toJson(subjects)
    }

    @TypeConverter
    fun toSubjectsList(subjectsString: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(subjectsString, type)
    }
}
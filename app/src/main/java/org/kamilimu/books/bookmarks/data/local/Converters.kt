package org.kamilimu.books.bookmarks.data.local

import androidx.room.TypeConverter

class Converters {
    /**
     * Converts a String to a List by splitting the String around occurrences of the specified
     * delimiter
     */
    @TypeConverter
    fun toListFromString(value: String): List<String> {
        return value.split(",")
    }

    /**
     * Converts a List to a String by joining the list items, separated by the specified delimiter
     */
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(",")
    }
}
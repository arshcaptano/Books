package org.kamilimu.books.viewbooks.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListToString(listObject: List<String>): String {
        return listObject.joinToString(",")
    }

    @TypeConverter
    fun toListFromString(value: String): List<String> {
        return value.split(",")
    }
}
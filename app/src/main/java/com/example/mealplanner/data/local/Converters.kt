package com.example.mealplanner.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate


class Converters {
    @TypeConverter
    fun fromTimestamp(value:String):LocalDate=value.let { LocalDate.parse(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate):String=date.toString()
}
package com.example.taskapp.data.Converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeConverter {
    @TypeConverter
    fun fromEpoch(epoch:Long):LocalDateTime{
        return LocalDateTime.ofEpochSecond(epoch,0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun toEpoch(timeObject:LocalDateTime):Long{
        return timeObject.toEpochSecond(ZoneOffset.UTC)
    }
}
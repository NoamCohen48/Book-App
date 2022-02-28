package com.example.mynewlibrary.data

import android.net.Uri
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromUri(value: Uri?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toUri(stringUri: String?): Uri? {
        if(stringUri == null){
            return null
        }
        return Uri.parse(stringUri)
    }
}
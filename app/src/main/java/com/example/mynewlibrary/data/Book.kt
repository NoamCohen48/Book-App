package com.example.mynewlibrary.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "books_table")
data class Book(
    var name: String,

    var author: String,

    var desc: String,

    var imageUri: Uri?,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(defaultValue = "0")
    var isAlreadyRead: Boolean = false

    @ColumnInfo(defaultValue = "0")
    var isWishlist: Boolean = false
}
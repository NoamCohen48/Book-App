package com.example.mynewlibrary.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    // ~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Query("SELECT * FROM books_table ORDER BY name ASC")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books_table WHERE isAlreadyRead = 1 ORDER BY name ASC")
    fun getAlreadyReadBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books_table WHERE isWishlist = 1 ORDER BY name ASC")
    fun getWishlistBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books_table WHERE id = :id")
    fun getBookById(id: Int): Flow<Book>

    // ~~~~~~~~~~~~~~~~~ ADDERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToAllBooks(word: Book)

    @Query("UPDATE books_table SET isAlreadyRead = 1 WHERE id = :id")
    suspend fun addToAlreadyReadBooks(id: Int)

    @Query("UPDATE books_table SET isWishlist = 1 WHERE id = :id")
    suspend fun addToWishlistBooks(id: Int)

    // ~~~~~~~~~~~~~~~~~ REMOVERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Delete
    suspend fun removeFromAllBooks(word: Book)

    @Query("UPDATE books_table SET isAlreadyRead = 0 WHERE id = :id")
    suspend fun removeFromAlreadyReadBooks(id: Int)

    @Query("UPDATE books_table SET isWishlist = 0 WHERE id = :id")
    suspend fun removeFromWishlistBooks(id: Int)

    @Query("DELETE FROM books_table")
    suspend fun removeAll()
}
package com.example.mynewlibrary.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
@WorkerThread
class BookRepository(private val bookDao: BookDao) {

    val allBooks = bookDao.getAllBooks().asLiveData()

    val alreadyReadBooks = bookDao.getAlreadyReadBooks().asLiveData()

    val wishlistBooks = bookDao.getWishlistBooks().asLiveData()

    // ~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//    fun getAllBooks(): Flow<List<Book>>{
//        return bookDao.getAllBooks()
//    }
//
//    fun getAlreadyReadBooks(): Flow<List<Book>>{
//        return bookDao.getAlreadyReadBooks()
//    }
//
//    fun getWishlistBooks(): Flow<List<Book>>{
//        return bookDao.getWishlistBooks()
//    }

    fun getBookById(id: Int): Flow<Book>{
        return bookDao.getBookById(id)
    }

    // ~~~~~~~~~~~~~~~~~ ADDERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    //@Suppress("RedundantSuspendModifier")
    suspend fun addToAllBooks(book: Book) {
        bookDao.addToAllBooks(book)
    }

    suspend fun addToAlreadyReadBooks(id: Int){
        bookDao.addToAlreadyReadBooks(id)
    }

    suspend fun addToWishlistBooks(id: Int){
        bookDao.addToWishlistBooks(id)
    }

    // ~~~~~~~~~~~~~~~~~ REMOVERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    suspend fun removeFromAllBooks(book: Book){
        bookDao.removeFromAllBooks(book)
    }

    suspend fun removeFromAlreadyReadBooks(id: Int){
        bookDao.removeFromAlreadyReadBooks(id)
    }

    suspend fun removeFromWishlistBooks(id: Int){
        bookDao.removeFromWishlistBooks(id)
    }

    suspend fun removeAll(){
        bookDao.removeAll()
    }


}
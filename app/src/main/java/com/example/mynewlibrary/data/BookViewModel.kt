package com.example.mynewlibrary.data

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    val allBooks = repository.allBooks

    val alreadyReadBooks = repository.alreadyReadBooks

    val wishlistBooks = repository.wishlistBooks

    // ~~~~~~~~~~~~~~~~~ GETTERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    fun getBookById(id: Int): LiveData<Book>{
        return repository.getBookById(id).asLiveData()
    }

    // ~~~~~~~~~~~~~~~~~ ADDERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    fun addToAllBooks(book: Book) {
        viewModelScope.launch {
            repository.addToAllBooks(book)
        }
    }

    fun addToAlreadyReadBooks(id: Int) {
        viewModelScope.launch {
            repository.addToAlreadyReadBooks(id)
        }
    }

    fun addToWishlistBooks(id: Int) = viewModelScope.launch {
        repository.addToWishlistBooks(id)
    }

    // ~~~~~~~~~~~~~~~~~ REMOVERS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    fun removeFromAllBooks(book: Book) = viewModelScope.launch {
        repository.removeFromAllBooks(book)
    }

    fun removeFromAlreadyReadBooks(id: Int) = viewModelScope.launch {
        repository.removeFromAlreadyReadBooks(id)
    }

    fun removeFromWishlistBooks(id: Int) = viewModelScope.launch {
        repository.removeFromWishlistBooks(id)
    }

    fun removeAll() = viewModelScope.launch {
        repository.removeAll()
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
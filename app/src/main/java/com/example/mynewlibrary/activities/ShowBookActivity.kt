package com.example.mynewlibrary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mynewlibrary.BooksApplication
import com.example.mynewlibrary.R
import com.example.mynewlibrary.data.Book
import com.example.mynewlibrary.data.BookViewModel
import com.example.mynewlibrary.data.BookViewModelFactory
import com.example.mynewlibrary.databinding.ActivityShowBookBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import android.view.MenuItem


class ShowBookActivity : AppCompatActivity() {
    companion object Tags {
        val BOOK_ID = "book_id"
    }

    private lateinit var binding: ActivityShowBookBinding

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory((application as BooksApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar.toolbar)
        binding.toolbar.toolbar.setLogo(R.mipmap.ic_library)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // get book to show
        val bookId = intent.getIntExtra(BOOK_ID, -1)
        assert(bookId != -1)

        bookViewModel.getBookById(bookId).observe(this, Observer {
            if(it != null){
                initView(it)
                initButtons(it)
            }
        })
    }

    private fun initView(book: Book) {
        // changing app title to book name capitelized
        title = book.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        binding.nameTextView.text = book.name
        binding.authorTextView.text = book.author
        binding.descTextView.text = book.desc

        Glide.with(this).load(book.imageUri).into(binding.imageView)
    }

    private fun initButtons(book: Book) {
        // Add To Already Read Button
        binding.addAlreadyReadBtn.isEnabled = !book.isAlreadyRead
        binding.addAlreadyReadBtn.setOnClickListener {
            bookViewModel.addToAlreadyReadBooks(book.id)
        }

        // Add To Wishlist Button
        binding.addWishlistBtn.isEnabled = !book.isWishlist
        binding.addWishlistBtn.setOnClickListener {
            bookViewModel.addToWishlistBooks(book.id)
        }

        // Delete Button
        binding.deleteBtn.setOnClickListener {
            // showing message
            showAlertDialog(book)
        }
    }

    private fun showAlertDialog(book: Book) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Delete ${book.name}")
            .setMessage("are you sure you want to delete ${book.name}")
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { dialog, which ->
                bookViewModel.removeFromAllBooks(book)
                finish()
            }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // todo: goto back activity from here
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
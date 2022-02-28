package com.example.mynewlibrary.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.mynewlibrary.BooksApplication
import com.example.mynewlibrary.R
import com.example.mynewlibrary.data.Book
import com.example.mynewlibrary.data.BookViewModel
import com.example.mynewlibrary.data.BookViewModelFactory
import com.example.mynewlibrary.databinding.ActivityAddBookBinding


class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory((application as BooksApplication).repository)
    }

    private var imageUri: Uri? = null
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imageUri = it
                binding.imageView.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar.toolbar)
        binding.toolbar.toolbar.setLogo(R.mipmap.ic_library)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // setting title
        title = "Add Book"

        // defining on click
        binding.addBtn.setOnClickListener {
            addBook()
        }

        binding.imageView.setOnClickListener {
            imagePicker.launch(arrayOf("image/*"))
        }
    }

    private fun addBook() {
        // fetching info
        val name: String = binding.nameEditText.text.toString()
        val author: String = binding.authorEditText.text.toString()
        val desc: String = binding.descEditText.text.toString()

        // create book
        val book = Book(name, author, desc, imageUri)

        // adding book
        bookViewModel.addToAllBooks(book)

        // showing message
        Toast.makeText(this, "Book has been added", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // todo: goto back activity from here
                onBackPressed()
//                val intent = Intent(this@AddBookActivity, MainActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
//                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
package com.example.mynewlibrary.models

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynewlibrary.activities.ShowBookActivity
import com.example.mynewlibrary.data.Book
import com.example.mynewlibrary.databinding.ListItemBookBinding

class BookRecViewAdapter(val context: Context) :
    RecyclerView.Adapter<BookRecViewAdapter.BookViewHolder>() {

    var books: List<Book> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class BookViewHolder(val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var book: Book? = null

        fun bind(givenBook: Book) {
            book = givenBook

            binding.nameTextView.text = givenBook.name
            binding.AuthorTextView.text = givenBook.author

            Glide.with(context).load(givenBook.imageUri).into(binding.imageView)

            binding.cardView.setOnClickListener {
                val intent = Intent(context, ShowBookActivity::class.java)
                intent.putExtra(ShowBookActivity.Tags.BOOK_ID, givenBook.id)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ListItemBookBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return BookViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(bookViewHolder: BookViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        bookViewHolder.bind(books[position])

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = books.size

}
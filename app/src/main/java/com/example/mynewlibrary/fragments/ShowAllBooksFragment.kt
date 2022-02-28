package com.example.mynewlibrary.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewlibrary.BooksApplication
import com.example.mynewlibrary.data.Book
import com.example.mynewlibrary.data.BookViewModel
import com.example.mynewlibrary.data.BookViewModelFactory
import com.example.mynewlibrary.databinding.FragmentShowAllBooksBinding
import com.example.mynewlibrary.models.BookRecViewAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ShowAllBooksFragment : Fragment() {
    private var _binding: FragmentShowAllBooksBinding? = null
    private val binding
        get() = _binding!!

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory((activity?.application as BooksApplication).repository)
    }

    private lateinit var adapter: BookRecViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("observer:", "show all books binding created")
        _binding = FragmentShowAllBooksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting RecView
        adapter = BookRecViewAdapter(requireContext())
        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recView)

        bookViewModel.allBooks.observe(viewLifecycleOwner, Observer { allBooks ->
            Log.d("observer:", "show all books observer")
            // Update the cached copy of the allBooks in the adapter.
            allBooks?.let { adapter.books = it }
            if (allBooks.isNotEmpty()) {
                binding.listEmptyTextView.visibility = View.INVISIBLE
            } else {
                binding.listEmptyTextView.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (viewHolder !is BookRecViewAdapter.BookViewHolder) {
                    return
                }
                showAlertDialog(viewHolder.book!!)
            }
        }

    private fun showAlertDialog(book: Book) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete ${book.name}")
            .setMessage("are you sure you want to delete ${book.name} entirely")
            .setNegativeButton("No") { dialog, which ->
                adapter.notifyDataSetChanged()
            }
            .setPositiveButton("Yes") { dialog, which ->
                bookViewModel.removeFromAllBooks(book)
            }
            .show()
    }
}
package com.example.mynewlibrary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mynewlibrary.BooksApplication
import com.example.mynewlibrary.R
import com.example.mynewlibrary.data.BookViewModel
import com.example.mynewlibrary.data.BookViewModelFactory
import com.example.mynewlibrary.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyNewLibrary_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar.toolbar)
        binding.toolbar.toolbar.setLogo(R.mipmap.ic_library)

        // getting nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        // top bar
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.showAllBooksFragment,
            R.id.wishlistBooksFragment,
            R.id.alreadyReadBooksFragment
        ).build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.floatingActionBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBookActivity::class.java)
            startActivity(intent)
        }

        // snackbar
        val snackbar = Snackbar.make(view, "swipe on book to delete", Snackbar.LENGTH_SHORT)
        snackbar.anchorView = binding.bottomNavigation
        snackbar.show()
    }
}
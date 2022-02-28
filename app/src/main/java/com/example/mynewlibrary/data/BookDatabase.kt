package com.example.mynewlibrary.data

import android.content.Context
import android.net.Uri
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Book::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): BookDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .addCallback(BookDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class BookDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.bookDao())
                }
            }
        }

        suspend fun populateDatabase(bookDao: BookDao) {
            // Delete all content here.
            bookDao.removeAll()

            // Add sample words.
            var book = Book(
                "harry potter",
                "someone",
                "just wow!",
                Uri.parse("https://images-na.ssl-images-amazon.com/images/I/91ocU8970hL.jpg")
            )
            bookDao.addToAllBooks(book)

            book = Book(
                "harry potter the sequel",
                "someone but better",
                "I am what I am, an' I'm not ashamed",
                Uri.parse("https://images-na.ssl-images-amazon.com/images/I/91ocU8970hL.jpg")
            )
            bookDao.addToAllBooks(book)

            book = Book(
                "avatar",
                "Michael DiMartino",
                "That's Rough Buddy",
                Uri.parse("https://i.kym-cdn.com/entries/icons/facebook/000/035/316/that'sroughbuddycover.jpg")
            )
            bookDao.addToAllBooks(book)

            book = Book(
                "SpongeBob",
                "nickelodeon",
                "Goodbye everyone, I'll remember you all in therapy",
                Uri.parse("https://api.time.com/wp-content/uploads/2019/08/caveman-spongebob-spongegar.png")
            )
            bookDao.addToAllBooks(book)

            book = Book(
                "War and Peace",
                "somebody important",
                "Nothing is so necessary for a young man as the company of intelligent women",
                Uri.parse("https://images-na.ssl-images-amazon.com/images/I/A1aDb5U5myL.jpg")
            )
            bookDao.addToAllBooks(book)
        }
    }
}


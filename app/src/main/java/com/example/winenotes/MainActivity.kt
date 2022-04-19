package com.example.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.winenotes.database.Note
import com.example.winenotes.databinding.ActivityMainBinding

val NOTES = mutableListOf<Note>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_title_menuoption -> sortByTitle()
            R.id.sort_by_last_modified_menuoption -> sortByLastModified()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortByTitle() {
        // TODO: Implement sort by title function
    }

    private fun sortByLastModified() {
        // TODO: Implement sort by last modified function
    }
}
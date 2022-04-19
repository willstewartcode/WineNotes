package com.example.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.winenotes.database.AppDatabase
import com.example.winenotes.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private var purpose : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // gets intent sent by main activity
        val intent = getIntent()
        purpose = intent.getStringExtra(getString(R.string.intent_purpose_key))

        setTitle("$purpose")
    }

    override fun onBackPressed() {
        // gets user input
        val title : String = binding.titleEdittext.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(
                applicationContext,
                getString(R.string.title_cannot_be_empty),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val noteText : String = binding.noteEdittext.text.toString().trim()

        // inserts input into database
        CoroutineScope(Dispatchers.IO).launch {
            val noteDao = AppDatabase.getDatabase(applicationContext)
                .noteDao()

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                // adds new note to database
            } else if (purpose.equals(getString(R.string.intent_purpose_update_note))) {
                TODO("Not implemented")
            }
        }

        super.onBackPressed()
    }
}
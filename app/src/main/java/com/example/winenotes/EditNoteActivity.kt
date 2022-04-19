package com.example.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.winenotes.database.AppDatabase
import com.example.winenotes.database.Note
import com.example.winenotes.databinding.ActivityEditNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding

    private var purpose : String? = ""
    private var noteId : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // gets intent sent by main activity
        val intent = getIntent()
        purpose = intent.getStringExtra(getString(R.string.intent_purpose_key))

        if (purpose.equals(getString(R.string.intent_purpose_update_note))) {
            noteId = intent.getLongExtra(
                getString(R.string.intent_key_note_id),
                -1
            )

            // gets note from db
            CoroutineScope(Dispatchers.IO).launch {
                val note = AppDatabase.getDatabase(applicationContext)
                    .noteDao()
                    .getNote(noteId)

                withContext(Dispatchers.Main) {
                    binding.titleEdittext.setText(note.title)
                    binding.noteEdittext.setText(note.notes)
                }
            }
        }

        title = "$purpose"
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

        val now = Date()
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        databaseDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateString : String = databaseDateFormat.format(now)

        // inserts input into database
        CoroutineScope(Dispatchers.IO).launch {
            val noteDao = AppDatabase.getDatabase(applicationContext)
                .noteDao()

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                // adds new note to database
                val note = Note(0, title, noteText, dateString)
                noteId = noteDao.addNote(note)
                Log.i("STATUS_NOTE", "Inserted note: ${note.id}, ${note.title}, ${note.notes}, ${note.lastModified}")
            } else if (purpose.equals(getString(R.string.intent_purpose_update_note))) {

            }

            val intent = Intent()
            intent.putExtra(
                getString(R.string.intent_key_note_id),
                noteId
            )

            withContext(Dispatchers.Main) {
                setResult(RESULT_OK, intent)
                super.onBackPressed()
            }
        }
    }
}
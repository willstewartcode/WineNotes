package com.example.winenotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.winenotes.database.AppDatabase
import com.example.winenotes.database.Note
import com.example.winenotes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val NOTES = mutableListOf<Note>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private lateinit var sortOrder: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerview.layoutManager = layoutManager

        val divider = DividerItemDecoration(
            applicationContext,
            layoutManager.orientation
        )
        binding.notesRecyclerview.addItemDecoration(divider)

        adapter = MyAdapter()
        binding.notesRecyclerview.adapter = adapter

        binding.addNoteImagebutton.setOnClickListener(AddNoteButtonListener())

        loadNotesByTitle()
    }

    private fun loadNotesByTitle() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val dao = db.noteDao()
            val results = dao.getNotesByTitle()

            withContext(Dispatchers.Main) {
                NOTES.clear()
                NOTES.addAll(results)
                adapter.notifyDataSetChanged()
            }

            for (note in results) {
                Log.i("STATUS_MAIN", "read $note")
            }
        }
    }

    private fun loadNotesByLastModified() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val dao = db.noteDao()
            val results = dao.getNotesByLastModified()

            withContext(Dispatchers.Main) {
                NOTES.clear()
                NOTES.addAll(results)
                adapter.notifyDataSetChanged()
            }

            for (note in results) {
                Log.i("STATUS_MAIN", "read $note")
            }
        }
    }

    inner class MyViewHolder(val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

//        init {
//            itemView.setOnClickListener(this)
//            itemView.setOnLongClickListener(this)
//        }

        fun setText(title: String, lastModified : String) {
            itemView.findViewById<TextView>(R.id.note_title_textview).text = title
            itemView.findViewById<TextView>(R.id.note_lastmodified_textview).append(" $lastModified")
        }
    }

    inner class MyAdapter() : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val note = NOTES[position]
            holder.setText(note.title, note.lastModified)
        }

        override fun getItemCount(): Int {
            return NOTES.size
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_title_menuoption -> loadNotesByTitle()
            R.id.sort_by_last_modified_menuoption -> loadNotesByLastModified()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class AddNoteButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            addNewNote()
        }
    }

    private val startForAddResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result : ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                loadNotesByLastModified()
            }
        }

    private fun addNewNote() {
        val intent = Intent(applicationContext, EditNoteActivity::class.java)
        intent.putExtra(
            getString(R.string.intent_purpose_key),
            getString(R.string.intent_purpose_add_note)
        )
        startForAddResult.launch(intent)
    }
}
package com.example.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.winenotes.database.Note
import com.example.winenotes.databinding.ActivityMainBinding

val NOTES = mutableListOf<Note>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
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
    }

    inner class MyViewHolder(val itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

//        init {
//            itemView.findViewById<View>(R.layout.item_view)
//                .setOnClickListener(this)
//        }

        fun setText(title: String, lastModified : String) {
            itemView.findViewById<TextView>(R.id.note_title_textview).text = title
            itemView.findViewById<TextView>(R.id.note_lastmodified_textview).append(" $lastModified")
        }

        override fun onClick(view: View?) {
            if(view != null) {

            }
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
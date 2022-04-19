package com.example.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.winenotes.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
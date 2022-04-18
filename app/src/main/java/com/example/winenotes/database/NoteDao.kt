package com.example.winenotes.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface NoteDao {
    // Gets all notes, sorted by title
    @Query("SELECT * FROM note ORDER BY title")
    fun getNotesByTitle() : List<Note>

    // Gets all notes, sorted by date last modified
    @Query("SELECT * FROM note ORDER BY lastModified")
    fun getNotesByLastModified() : List<Note>
}
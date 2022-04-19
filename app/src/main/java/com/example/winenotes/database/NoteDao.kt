package com.example.winenotes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    /**
     * @param note the note object to insert
     *          id should be 0.
     *
     * @return ID of note object just inserted
     */
    @Insert
    fun addNote(note : Note) : Long

    // Gets all notes, sorted by title
    @Query("SELECT * FROM note ORDER BY title")
    fun getNotesByTitle() : List<Note>

    // Gets all notes, sorted by date last modified
    @Query("SELECT * FROM note ORDER BY lastModified")
    fun getNotesByLastModified() : List<Note>
}
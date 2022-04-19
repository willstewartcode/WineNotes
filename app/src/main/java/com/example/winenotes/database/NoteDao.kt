package com.example.winenotes.database

import androidx.room.*

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

    // ID cannot be 0
    @Update
    fun updateNote(note : Note)

    @Delete
    fun deleteNote(note : Note)

    // Gets all notes, sorted by title
    @Query("SELECT * FROM note ORDER BY title")
    fun getNotesByTitle() : List<Note>

    // Gets all notes, sorted by date last modified
    @Query("SELECT * FROM note ORDER BY lastModified DESC")
    fun getNotesByLastModified() : List<Note>

    // Gets specified note
    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNote(noteId : Long) : Note
}
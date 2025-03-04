package com.example.noteapp.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteapp.Entity.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes ORDER BY id DESC")
     fun getAllNotes(): List<Notes>

    @Delete
    suspend fun deleteNote(note: Notes)

}
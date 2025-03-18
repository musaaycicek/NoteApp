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

    @Query("DELETE FROM notes WHERE id = :noteId")
     fun deleteNote(noteId:Int)

     @Query("UPDATE notes Set isCompleted=:isComplete,completedNote=:completedNote WHERE id=:id")
     fun noteComplete(id:Int,isComplete:Boolean,completedNote:String)

     @Query("SELECT * FROM notes WHERE isCompleted=1")
     fun getCompletedNotes(): List<Notes>

}
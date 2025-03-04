package com.example.noteapp.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var note: String,
    var isCompleted: Boolean = false,
    var completedNote: String? = null

)

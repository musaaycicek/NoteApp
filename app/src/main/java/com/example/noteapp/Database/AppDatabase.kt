package com.example.noteapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.Dao.NotesDao
import com.example.noteapp.Entity.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao



    // Singleton Pattern
    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?=null

        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"

                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance

                instance

            }
        }
    }

}
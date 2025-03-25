package com.example.noteapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.Dao.NotesDao
import com.example.noteapp.Database.AppDatabase
import com.example.noteapp.Entity.Notes
import com.example.noteapp.adapters.noteAdapter
import com.example.noteapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var userDao: NotesDao
    private lateinit var adapter:noteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            showCustomDialog()
        }
        database = AppDatabase.getDatabase(this)
        userDao = database.notesDao()

        adapter=noteAdapter(userDao.getAllNotes().toList())
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        binding.recyclerView.setHasFixedSize(true)

        binding.searchView.setOnClickListener{
            val intent=Intent(this,SearchActivity::class.java)
            startActivity(intent)

        }



    }

    fun showCustomDialog() {


        val dialog = Dialog(this)
        dialog.setContentView(R.layout.editnote_pop)
        val window = dialog.window
        window?.let {
            val layoutParams = it.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT // Genişlik
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // Yükseklik
            layoutParams.gravity = Gravity.BOTTOM // Dialog alt kısımda başladı
            it.attributes = layoutParams
        }

        val cancelButton = dialog.findViewById<ImageButton>(R.id.exitButton)
        cancelButton.setOnClickListener {
//            val intent= Intent(this,MainActivity::class.java)
//            startActivity(intent)
            dialog.dismiss()
        }

        val enterText = dialog.findViewById<EditText>(R.id.enterNoteText)
        val saveButton = dialog.findViewById<Button>(R.id.savebutton)
        saveButton.setOnClickListener {
            if (enterText.text.toString().isNotEmpty()) {

                Toast.makeText(this@MainActivity, "Kaydedildi1", Toast.LENGTH_SHORT).show()
                val notes = Notes(note = enterText.text.toString())
                lifecycleScope.launch {
                    userDao.insertNote(notes)

                    withContext(Dispatchers.Main){
                      adapter.setNotes1(userDao.getAllNotes().toList())
                        Toast.makeText(this@MainActivity, "Kaydedildi2", Toast.LENGTH_SHORT).show()
                    }


                }
                dialog.dismiss()



            }
        }

        dialog.show()

    }
}
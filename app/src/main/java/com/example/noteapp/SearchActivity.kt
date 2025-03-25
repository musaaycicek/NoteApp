package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.Dao.NotesDao
import com.example.noteapp.Database.AppDatabase
import com.example.noteapp.adapters.searchAdapter
import com.example.noteapp.databinding.ActivitySearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {


    private lateinit var database: AppDatabase
    private lateinit var userDao: NotesDao
    private lateinit var adapter: searchAdapter

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)
        userDao = database.notesDao()

//        adapter = noteAdapter(userDao.searchNotes(binding.searchView.query.toString()))
//        binding.searchcycle.adapter = adapter
//        binding.searchcycle.layoutManager = LinearLayoutManager(this)

        binding.imageButton.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        adapter= searchAdapter(this@SearchActivity)
        binding.searchcycle.adapter=adapter
        binding.searchcycle.layoutManager= LinearLayoutManager(this)
        binding.searchcycle.setHasFixedSize(true)


        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let{
//                    searchNotes(it)
//                }

                if(query!=null){
                    searchNotes(query)
                }
                    return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
              //  userDao.searchNotes("%$newText%")
//                newText?.let{
//                    searchNotes(it)
//                }

                if(newText!=null){
                    searchNotes(newText)
                }
                return true
            }
        })




    }

    fun searchNotes(query:String){
        lifecycleScope.launch {

            withContext(Dispatchers.Main){
                adapter.setNotes(userDao.searchNotes(query))
            }

        }
    }
}
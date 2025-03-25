package com.example.noteapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Entity.Notes
import com.example.noteapp.databinding.ActivitySearchBinding
import com.example.noteapp.databinding.SearchRecyclerviewRowBinding



class searchAdapter(context: Context): RecyclerView.Adapter<searchAdapter.ViewHolder>() {

    private var notesList: List<Notes> = listOf()

    class ViewHolder(val binding: SearchRecyclerviewRowBinding):RecyclerView.ViewHolder(binding.root){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=SearchRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note=notesList[position]
        holder.binding.searchNoteText.text=note.note


    }

    fun setNotes(notes: List<Notes>){
        notesList=notes
        notifyDataSetChanged()
    }
}
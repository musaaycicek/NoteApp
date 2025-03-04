package com.example.noteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Entity.Notes
import com.example.noteapp.databinding.RecyclerviewRowsBinding

class noteAdapter(val list: List<Notes>): RecyclerView.Adapter<noteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val binding: RecyclerviewRowsBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = RecyclerviewRowsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var  position=list[position]

        holder.binding.noteText.text=position.note
        holder.binding.pointBox.isChecked = position.isCompleted
        holder.binding.pointBox.setOnCheckedChangeListener(null)

        holder.binding.pointBox.setOnClickListener {
            holder.binding.pointBox.isChecked = !holder.binding.pointBox.isChecked
        }

//            if (holder.binding.pointBox.isChecked==false){
//                holder.binding.pointBox.setOnClickListener {
//                    holder.binding.pointBox.isChecked = true
//                }
//
//
//            }else if (holder.binding.pointBox.isChecked==true){
//                holder.binding.pointBox.setOnClickListener {
//                    holder.binding.pointBox.isChecked = false
//                }
//
//            }










    }
}
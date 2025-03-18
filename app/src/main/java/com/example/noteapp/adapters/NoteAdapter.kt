package com.example.noteapp.adapters

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Dao.NotesDao
import com.example.noteapp.Database.AppDatabase
import com.example.noteapp.Entity.Notes
import com.example.noteapp.R
import com.example.noteapp.databinding.RecyclerviewRowsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class noteAdapter(val list: List<Notes>) : RecyclerView.Adapter<noteAdapter.NoteViewHolder>() {

    private lateinit var database: AppDatabase
    private lateinit var userDao: NotesDao


    class NoteViewHolder(val binding: RecyclerviewRowsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            RecyclerviewRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val anim= AnimationUtils.loadAnimation(holder.itemView.context, R.anim.completed_note_anim)
        val alert = AlertDialog.Builder(holder.itemView.context)
        alert.setTitle("Silme İşlemi")
        alert.setMessage("Silmek istediğinize emin misiniz?")

        database = AppDatabase.getDatabase(holder.itemView.context)
        userDao = database.notesDao()


        var position = list[position]

        holder.binding.noteText.text = position.note

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.binding.imageButton2.visibility = View.VISIBLE
                holder.binding.imageButton2.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {

                        alert.setPositiveButton("Evet", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                userDao.deleteNote(position.id)
                                notifyDataSetChanged()
                            }

                        })

                        alert.setNegativeButton("Hayır", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog?.dismiss()
                            }
                        })


                    }

                    alert.show()
                }
                return true
            }

        })

        holder.binding.checkBox2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked: Boolean ->
            if (isChecked) {

                holder.binding.checkBox2.isChecked = true
                userDao.noteComplete(position.id, true, position.note)

                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        holder.binding.noteText.visibility=View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animation?) {

                        holder.binding.checkBox2.visibility=View.GONE

                        holder.binding.noteText.visibility=View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("Not yet implemented")
                    }



                })
                holder.binding.noteText.startAnimation(anim)



            } else {
                holder.binding.checkBox2.isChecked = false
                holder.binding.imageButton2.visibility = View.GONE
            }


        })


    }
}
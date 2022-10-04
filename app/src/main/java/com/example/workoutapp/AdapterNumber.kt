package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.RecyclerViewItemBinding

class AdapterNumber(val items: ArrayList<Exercises>): RecyclerView.Adapter<AdapterNumber.ViewHolder>() {

    class ViewHolder(binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val number = binding.iconNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Exercises = items[position]
        holder.number.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
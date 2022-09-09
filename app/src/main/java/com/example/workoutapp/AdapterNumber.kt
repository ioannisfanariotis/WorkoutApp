package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ExerciseNumberBinding

class AdaptorNumber(val items: ArrayList<Exercise>): RecyclerView.Adapter<AdaptorNumber.ViewHolder>() {

    class ViewHolder(binding: ExerciseNumberBinding): RecyclerView.ViewHolder(binding.root){
        val number = binding.iconNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ExerciseNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Exercise = items[position]
        holder.number.text = model.getId().toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
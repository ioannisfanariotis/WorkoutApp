package com.example.workoutapp.views.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.utils.Exercises
import com.example.workoutapp.R
import com.example.workoutapp.databinding.RecyclerViewItemBinding

class AdapterNumber(private val items: ArrayList<Exercises>): RecyclerView.Adapter<AdapterNumber.ViewHolder>() {

    class ViewHolder(binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val number = binding.iconNumber
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Exercises = items[position]
        holder.number.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.number.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.selected_number_background
                )
                holder.number.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted() -> {
                holder.number.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.accent_background)
                holder.number.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.number.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.number_background)
                holder.number.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
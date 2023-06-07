package com.example.workoutapp.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.databinding.RecyclerViewBinding

class Adapter(private val items: ArrayList<String>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(binding: RecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val layout = binding.rvLayout
        val pos = binding.rvPos
        val item = binding.rvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items[position]
        holder.pos.text = (position + 1).toString()
        holder.item.text = date

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        else
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
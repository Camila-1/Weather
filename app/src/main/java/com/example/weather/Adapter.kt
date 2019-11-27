package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val items: List<Weather>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Weather) {

        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = with(parent){
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, this, false))
    }
}
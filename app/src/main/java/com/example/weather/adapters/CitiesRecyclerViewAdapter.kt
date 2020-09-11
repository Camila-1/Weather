package com.example.weather.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.db.City
import kotlinx.android.synthetic.main.city_item.view.*
import javax.inject.Inject


class CitiesRecyclerViewAdapter @Inject constructor(private val items: List<City>?)
    : RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: City?) = with(itemView) {
            city_name.text = item?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = with(parent) {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.city_item, this, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position))

    override fun getItemCount(): Int = items?.size ?: 0

}

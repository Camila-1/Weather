package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.db.City
import kotlinx.android.synthetic.main.search_city_item.view.*

class FoundCityRecyclerViewAdapter(private val items: List<City>?) :
    RecyclerView.Adapter<FoundCityRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private  val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: City?) = with(itemView) {
            found_city.text = item?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = with(parent) {
        return ViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.search_city_item, this, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position))

    override fun getItemCount(): Int = items?.size ?: 0
}
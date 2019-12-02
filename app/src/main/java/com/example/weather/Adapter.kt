package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.response.WeatherData
import kotlinx.android.synthetic.main.list_item.view.*


class Adapter(private val items: List<WeatherData>, private val callback: (WeatherData) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: WeatherData, listener: (WeatherData) -> Unit) = with(itemView) {

            image.setImageResource(R.drawable.download)
            degrees.text = item.main.temp.toString()
            status.text = item.weather[0].main
            date.text = item.dateTime
            setOnClickListener{listener(item)}
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = with(parent){
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, this, false))
    }
}
package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.response.WeatherData
import kotlinx.android.synthetic.main.list_item.view.*



class Adapter(private val items: ArrayList<WeatherData>, private val callback: (WeatherData) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WeatherData, listener: (WeatherData) -> Unit) = with(itemView) {
            image.setImageResource(resources.getIdentifier("icon_${item.weather[0].icon}", "drawable", context.packageName))
            degree.text = Math.round(item.main.temp).toString().plus(SharedPreferenceHolder.getTemperatureUnit(context))
            description.text = item.weather[0].description.split(' ').joinToString(" ") { it.capitalize() }
            date.text = formatDate(item.dateTime, context)
            setOnClickListener{listener(item)}
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = with(parent){
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, this, false))
    }

}
package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.network.response.WeatherData
import com.example.weather.settings.SharedPreferenceHolder
import com.example.weather.utils.formatDate
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*
import kotlin.math.roundToInt


class Adapter(private val items: List<WeatherData>?, private val callback: (WeatherData) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WeatherData?, listener: (WeatherData) -> Unit) = with(itemView) {
            if (item == null) return@with
            image.setImageResource(resources.getIdentifier("icon_${item.weather[0].icon}", "drawable", context.packageName))
            degree.text = item.main.temp.roundToInt().toString().plus(SharedPreferenceHolder(context).getTemperatureUnit)
            description.text = item.weather[0].description.split(' ').joinToString(" ") {
                it.capitalize(
                    Locale.ROOT) }
            date.text = formatDate(item.dateTime, context)
            setOnClickListener{listener(item)}
        }
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position), callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = with(parent){
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, this, false))
    }

}
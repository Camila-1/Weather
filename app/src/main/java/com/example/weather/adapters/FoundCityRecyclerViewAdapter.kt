package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Nel
import com.example.weather.R
import com.example.weather.network.google_api.response.Variant
import com.example.weather.utils.Weather
import com.example.weather.utils.getDrawableIdentifier
import com.example.weather.utils.getLayoutIdentifier
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.search_city_item.view.*
import java.util.*
import kotlin.math.roundToInt

class FoundCityRecyclerViewAdapter(private val nel: Nel<Pair<Variant, Optional<Weather>>>) :
    RecyclerView.Adapter<FoundCityRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pair: Pair<Variant, Optional<Weather>>) = with(itemView){
            found_city.text = pair.first.description
            pair.second.map {
                val weatherByday = it.byDay()

                it.minMaxByDay(weatherByday[0]).let {
                    day_of_week_0.text = it.first.dayOfWeek.name
                    image_0.setImageResource(resources.getIdentifier("icon_${it.second.icon}","drawable", context.packageName))
                    max_0.text = it.second.max.roundToInt().toString()
                    min_0.text = it.second.min.roundToInt().toString()
                }

                it.minMaxByDay(weatherByday[1]).let {
                    day_of_week_1.text = it.first.dayOfWeek.name
                    image_1.setImageResource(resources.getIdentifier("icon_${it.second.icon}","drawable", context.packageName))
                    max_1.text = it.second.max.roundToInt().toString()
                    min_1.text = it.second.min.roundToInt().toString()
                }

                it.minMaxByDay(weatherByday[2]).let {
                    day_of_week_2.text = it.first.dayOfWeek.name
                    image_2.setImageResource(resources.getIdentifier("icon_${it.second.icon}","drawable", context.packageName))
                    max_2.text = it.second.max.roundToInt().toString()
                    min_2.text = it.second.min.roundToInt().toString()
                }

                it.minMaxByDay(weatherByday[3]).let {
                    day_of_week_3.text = it.first.dayOfWeek.name
                    image_3.setImageResource(resources.getIdentifier("icon_${it.second.icon}","drawable", context.packageName))
                    max_3.text = it.second.max.roundToInt().toString()
                    min_3.text = it.second.min.roundToInt().toString()
                }

                it.minMaxByDay(weatherByday[4]).let {
                    day_of_week_4.text = it.first.dayOfWeek.name
                    image_4.setImageResource(resources.getIdentifier("icon_${it.second.icon}","drawable", context.packageName))
                    max_4.text = it.second.max.roundToInt().toString()
                    min_4.text = it.second.min.roundToInt().toString()
                }
            }.orElse(Unit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = with(parent) {
        return ViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.search_city_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(nel[position])

    override fun getItemCount(): Int = nel.size
}

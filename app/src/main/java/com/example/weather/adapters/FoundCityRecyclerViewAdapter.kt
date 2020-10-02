package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Nel
import com.example.weather.R
import com.example.weather.network.google_api.response.Variant
import com.example.weather.utils.Weather
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.search_city_item.view.*
import kotlinx.android.synthetic.main.search_city_item_with_weather_data.view.*
import kotlinx.android.synthetic.main.search_city_weather_table.view.*
import java.util.*
import kotlin.math.roundToInt

class FoundCityRecyclerViewAdapter(
    private val nel: Nel<Pair<Variant, Optional<Weather>>>,
    private val addCityCallback: (Pair<Variant, Optional<Weather>>, position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FIRST_ITEM = 0
        const val OTHER_ITEM = 1
    }

    inner class FirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            pair: Pair<Variant, Optional<Weather>>,
            addCityCallback: (Pair<Variant, Optional<Weather>>, Int) -> Unit,
            position: Int
        ) = with(itemView) {
            pair.first.let {
                found_city.text = it.structuredFormatting.mainText
                description.text = it.structuredFormatting.secondaryText
                setButtonView(add_city_button, it.isAdded)
            }

            pair.second.map { weather ->
                weather_table.inflate()

                val weatherByday = weather.byDay()

                add_city_button.setOnClickListener { addCityCallback.invoke(pair, position) }

                weather.minMaxByDay(weatherByday[0]).let {
                    day_of_week_0.text = it.first.dayOfWeek.name.subSequence(0, 3)
                    image_0.setImageResource(
                        resources.getIdentifier(
                            "icon_${it.second.icon}",
                            "drawable",
                            context.packageName
                        )
                    )
                    max_0.text = it.second.max.roundToInt().toString()
                    min_0.text = it.second.min.roundToInt().toString()
                }

                weather.minMaxByDay(weatherByday[1]).let {
                    day_of_week_1.text = it.first.dayOfWeek.name.subSequence(0, 3)
                    image_1.setImageResource(
                        resources.getIdentifier(
                            "icon_${it.second.icon}",
                            "drawable",
                            context.packageName
                        )
                    )
                    max_1.text = it.second.max.roundToInt().toString()
                    min_1.text = it.second.min.roundToInt().toString()
                }

                weather.minMaxByDay(weatherByday[2]).let {
                    day_of_week_2.text = it.first.dayOfWeek.name.subSequence(0, 3)
                    image_2.setImageResource(
                        resources.getIdentifier(
                            "icon_${it.second.icon}",
                            "drawable",
                            context.packageName
                        )
                    )
                    max_2.text = it.second.max.roundToInt().toString()
                    min_2.text = it.second.min.roundToInt().toString()
                }

                weather.minMaxByDay(weatherByday[3]).let {
                    day_of_week_3.text = it.first.dayOfWeek.name.subSequence(0, 3)
                    image_3.setImageResource(
                        resources.getIdentifier(
                            "icon_${it.second.icon}",
                            "drawable",
                            context.packageName
                        )
                    )
                    max_3.text = it.second.max.roundToInt().toString()
                    min_3.text = it.second.min.roundToInt().toString()
                }

                weather.minMaxByDay(weatherByday[4]).let {
                    day_of_week_4.text = it.first.dayOfWeek.name.subSequence(0, 3)
                    image_4.setImageResource(
                        resources.getIdentifier(
                            "icon_${it.second.icon}",
                            "drawable",
                            context.packageName
                        )
                    )
                    max_4.text = it.second.max.roundToInt().toString()
                    min_4.text = it.second.min.roundToInt().toString()
                }
            }
        }
    }

    inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            pair: Pair<Variant, Optional<Weather>>,
            addCityCallback: (Pair<Variant, Optional<Weather>>, Int) -> Unit,
            position: Int
        ) = with(itemView) {
            pair.first.let {
                found_city.text = it.structuredFormatting.mainText
                description.text = it.structuredFormatting.secondaryText
                setButtonView(add_city_button, it.isAdded)
            }

            pair.second.map { weather ->
                add_city_button.setOnClickListener { addCityCallback.invoke(pair, position) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        with(parent) {
            return when (viewType) {
                FIRST_ITEM -> {
                    FirstViewHolder(
                        LayoutInflater.from(context)
                            .inflate(R.layout.search_city_item_with_weather_data, parent, false)
                    )
                }
                else -> {
                    OtherViewHolder(
                        LayoutInflater.from(context)
                            .inflate(R.layout.search_city_item_without_weather_data, parent, false)
                    )
                }
            }
        }

    override fun getItemCount(): Int = nel.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) FIRST_ITEM
        else OTHER_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FIRST_ITEM -> {
                (holder as FirstViewHolder).bind(nel[position], addCityCallback, position)
            }
            else -> {
                (holder as OtherViewHolder).bind(nel[position], addCityCallback, position)
            }
        }
    }

    private fun setButtonView(button: MaterialButton, isCityAdded: Boolean) {
        button.apply {
            if (isCityAdded) {
                setIconResource(R.drawable.ic_baseline_chevron_right_24)
                text = resources.getString(R.string.added)
            }
            else setIconResource(R.drawable.ic_baseline_add_24)
        }

    }
}

package com.example.weather.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.weather.R
import com.example.weather.SharedPreferenceHolder
import com.example.weather.formatDate
import com.example.weather.response.WeatherData
import kotlinx.android.synthetic.main.fragment_weather_details.*
import kotlinx.android.synthetic.main.fragment_weather_details.view.*

/**
 * A simple [Fragment] subclass.
 */
class WeatherDetailsFragment : Fragment() {
    private var selectedItem: WeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedItem = arguments?.getParcelable("selectedItem")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = selectedItem ?: return

        val temperatureUnit = SharedPreferenceHolder(context!!).getTemperatureUnit
        val distanceUnit = SharedPreferenceHolder(context!!).getDistanceUnit
        item.dateTime.let { date.text = formatDate(it, context!!) }
        icon.setImageResource(resources.getIdentifier("icon_${item.weather[0].icon}", "drawable", context?.packageName))
        item.main.temp.let { current_weather.text = Math.round(it).toString().plus(temperatureUnit) }
        view.min_max_temp.text = Math.round(item.main.tempMin).toString().plus(temperatureUnit)
            .plus(" / ")
            .plus(Math.round(item.main.tempMax).toString())
            .plus(temperatureUnit)
        item.main.pressure.let { pressure.text = Math.round(it).toString().plus(" hPa") }
        item.main.humidity.let { humidity.text = Math.round(it).toString().plus("%") }
        item.wind.speed.let { wind_speed.text = Math.round(it).toString().plus(distanceUnit) }
        item.wind.deg.let { wind_deg.text = Math.round(it).toString().plus("°") }
        view.clouds.text = item.clouds?.get("all")?.let(Math::round)?.let { if (it == 0L) null else it.toString().plus("%")} ?: "-"
        view.rain.text = item.rain?.get("3h")?.let(Math::round)?.let { if (it == 0L) null else it.toString().plus(" мм")} ?: "-"
        view.snow.text = item.snow?.get("3h")?.let(Math::round)?.let { if (it == 0L) null else it.toString().plus(" мм")} ?: "-"

    }

    companion object {
        fun newInstance(item: WeatherData?) : Fragment = WeatherDetailsFragment().apply {
            arguments = bundleOf("selectedItem" to item)
        }
    }

}

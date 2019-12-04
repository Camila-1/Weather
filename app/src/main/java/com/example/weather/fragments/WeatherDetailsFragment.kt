package com.example.weather.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.weather.R
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
        icon.setImageResource(resources.getIdentifier("icon_${item.weather[0].icon}", "drawable", context?.packageName))
        item.main.temp.let { current_weather.text = it.toString() }
        view.min_max_temp.text = item.main.tempMin.toString().plus(" / ").plus(item.main.tempMax.toString())
        item.main.pressure.let { pressure.text = it.toString() }
        item.main.humidity.let { humidity.text = it.toString() }
        item.wind.speed.let { wind_speed.text = it.toString() }
        item.wind.deg.let { wind_deg.text = it.toString() }
        view.clouds.text = item.clouds?.get("all")?.toString() ?: "-"
        view.rain.text = item.rain?.get("3h")?.toString() ?: "-"
        view.snow.text = item.snow?.get("3h")?.toString() ?: "-"
    }

    companion object {
        fun newInstance(item: WeatherData?) : Fragment = WeatherDetailsFragment().apply {
            arguments = bundleOf("selectedItem" to item)
        }
    }

}

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
        selectedItem?.main?.temp.let { temperature.text = it.toString() }
        selectedItem?.main?.tempMin.let { min_temperature.text = it.toString() }
        selectedItem?.main?.tempMax.let { max_temperature.text = it.toString() }
        selectedItem?.main?.pressure.let { pressure.text = it.toString() }
        selectedItem?.main?.humidity.let { humidity.text = it.toString() }
        selectedItem?.wind?.speed.let { wind_speed.text = it.toString() }
        selectedItem?.wind?.deg.let { wind_deg.text = it.toString() }
        selectedItem?.rain.let { rain.text = it.toString() }
    }

    companion object {
        fun newInstance(item: WeatherData) : Fragment = WeatherDetailsFragment().apply {
            arguments = bundleOf("selectedItem" to item)
        }
    }

}

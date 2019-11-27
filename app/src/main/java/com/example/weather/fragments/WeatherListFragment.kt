package com.example.weather.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.Adapter
import com.example.weather.R
import com.example.weather.Weather
import kotlinx.android.synthetic.main.fragment_weather_list.*

/**
 * A simple [Fragment] subclass.
 */
class WeatherListFragment : Fragment() {
    val emptyList = emptyList<Weather>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = Adapter(emptyList)
    }
}

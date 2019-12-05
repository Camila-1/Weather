package com.example.weather.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.*
import androidx.preference.PreferenceManager
import com.example.weather.response.WeatherData
import kotlinx.android.synthetic.main.fragment_weather_list.*


class WeatherListFragment : Fragment() {

    private var list: ArrayList<WeatherData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arguments?.getParcelableArrayList<WeatherData>("list") ?: arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        PreferenceManager.setDefaultValues(context, R.xml.root_preferences, false)
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = Adapter(list) {
            (activity as MainActivity).itemClicked(it)
        }
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        swipe.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary)
        swipe.setOnRefreshListener { (activity as MainActivity).updateWeatherData() }
    }



    companion object{
        fun newInstance(list: List<WeatherData>?): Fragment = WeatherListFragment().apply {
            arguments = bundleOf("list" to list)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

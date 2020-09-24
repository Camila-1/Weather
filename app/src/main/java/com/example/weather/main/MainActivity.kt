package com.example.weather.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.application.WeatherApplication
import com.example.weather.city_management.CityManagementFragment
import com.example.weather.city_management.CitySearchFragment
import com.example.weather.city_weather.CityWeatherFragment
import com.example.weather.extensions.injectViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var eventViewModel: EventViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        WeatherApplication.appComponent.inject(this)

        eventViewModel = injectViewModel(viewModelFactory)

        super.onCreate(savedInstanceState)

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                .or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        setContentView(R.layout.activity_main)

        eventViewModel.state.observe(this, Observer {

            val transaction = supportFragmentManager.beginTransaction()
            when(it) {
                EventViewModel.State.CITY_MANAGEMENT_FRAGMENT -> transaction
                    .replace(R.id.fragment_container, CityManagementFragment()).commit()
                EventViewModel.State.CITY_WEATHER_FRAGMENT -> transaction
                    .replace(R.id.fragment_container, CityWeatherFragment()).commit()
                EventViewModel.State.SEARCH_CITY -> transaction
                    .replace(R.id.fragment_container, CitySearchFragment()).commit()
                else -> {}
            }
        })
    }
}

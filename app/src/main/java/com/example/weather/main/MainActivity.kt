package com.example.weather.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.application.WeatherApplication
import com.example.weather.city_management.CityManagementFragment
import com.example.weather.city_management.CitySearchFragment
import com.example.weather.city_management.WeatherDetailsFragment
import com.example.weather.city_weather.CityWeatherFragment
import com.example.weather.extensions.injectViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var navigationViewModel: NavigationViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        WeatherApplication.appComponent.inject(this)

        navigationViewModel = injectViewModel(viewModelFactory)

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

        navigationViewModel.state.observe(this, Observer {

            val transaction = supportFragmentManager.beginTransaction()
            when (it) {
                NavigationViewModel.Screen.CITY_MANAGEMENT -> transaction
                    .replace(R.id.fragment_container, CityManagementFragment())
                    .addToBackStack(null).commit()
                NavigationViewModel.Screen.CITY_WEATHER -> transaction
                    .replace(R.id.fragment_container, CityWeatherFragment())
                    .addToBackStack(null).commit()
                NavigationViewModel.Screen.SEARCH_CITY -> transaction
                    .replace(R.id.fragment_container, CitySearchFragment())
                    .addToBackStack(null).commit()
                NavigationViewModel.Screen.DETAILS -> transaction
                    .replace(R.id.fragment_container, WeatherDetailsFragment())
                    .addToBackStack(null)
                    .commit()
                else -> {
                }
            }
        })
    }
}

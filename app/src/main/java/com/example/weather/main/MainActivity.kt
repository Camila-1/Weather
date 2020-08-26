package com.example.weather.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weather.LocationProvider
import com.example.weather.R
import com.example.weather.adapters.StateAdapter
import com.example.weather.fragments.LonelyCloudFragment
import com.example.weather.network.WeatherRequest
import com.example.weather.network.response.WeatherData
import com.example.weather.network.response.WeatherResponse
import com.example.weather.settings.SettingsActivity
import com.example.weather.settings.SharedPreferenceHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE = 1
    }

    private val weatherViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())
            .get(WeatherViewModel::class.java)
    }

    private val locationService: LocationProvider = LocationProvider(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

        locationService.startLocationUpdates()

        viewPager.adapter = StateAdapter(this)

        supportActionBar?.title = null
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationService.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

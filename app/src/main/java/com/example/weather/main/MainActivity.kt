package com.example.weather.main

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weather.LocationProvider
import com.example.weather.R
import com.example.weather.adapters.StateAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val REQUEST_CODE = 1
    }

    private val citiesViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())
            .get(CitiesViewModel::class.java)
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

package com.example.weather

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.fragments.WeatherDetailsFragment
import com.example.weather.fragments.WeatherListFragment
import com.example.weather.response.WeatherData

class MainActivity : AppCompatActivity() {
    private var checkedItem: WeatherData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, WeatherListFragment()).commit()
    }

    fun itemClicked(item: WeatherData) {
        checkedItem = item
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val orientation: Int = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                .commit()
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.fragment, WeatherDetailsFragment.newInstance(item))
                .commit()
        }
    }
}

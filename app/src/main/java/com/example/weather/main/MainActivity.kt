package com.example.weather.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.service.autofill.Validators.or
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.weather.LocationProvider
import com.example.weather.R
import com.example.weather.adapters.StateAdapter
import com.example.weather.application.WeatherApplication
import com.example.weather.db.City
import com.example.weather.extensions.injectViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.example.weather.permissions.PermissionProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_weather.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionProvider: PermissionProvider

    @Inject
    lateinit var locationProvider: LocationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        WeatherApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)

        citiesViewModel = injectViewModel(viewModelFactory)

        citiesViewModel.addCity(City(1, "Tashkent"))

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                .or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.plus)

        citiesViewModel.cities.observe(this, {
            viewPager.adapter = StateAdapter(this, it)
        })


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionProvider.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
            R.id.add_city_icon -> { true }
            R.id.settings -> { true }
            else -> { super.onOptionsItemSelected(item) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

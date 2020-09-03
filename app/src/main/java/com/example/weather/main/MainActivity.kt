package com.example.weather.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.weather.LocationProvider
import com.example.weather.R
import com.example.weather.adapters.StateAdapter
import com.example.weather.extensions.injectViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.example.weather.permissions.PermissionProvider
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionProvider: PermissionProvider

    @Inject
    lateinit var locationProvider: LocationProvider

    //FIXME: viewModelFactory HAS NOT BEEN INITIALIZED
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        citiesViewModel = injectViewModel(viewModelFactory)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false)

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
}

package com.example.weather.city_management

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.application.WeatherApplication
import com.example.weather.extensions.injectViewModel
import com.example.weather.main.NavigationViewModel
import javax.inject.Inject

class WeatherDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesManagementViewModel

    lateinit var navigationViewModel: NavigationViewModel

    override fun onAttach(context: Context) {
        WeatherApplication.appComponent.inject(this)

        citiesViewModel = injectViewModel(viewModelFactory)
        navigationViewModel = injectViewModel(viewModelFactory)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = false
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_details, container, false)
    }
}
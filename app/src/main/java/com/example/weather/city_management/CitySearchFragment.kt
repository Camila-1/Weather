package com.example.weather.city_management

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.adapters.FoundCityRecyclerViewAdapter
import com.example.weather.application.WeatherApplication
import com.example.weather.extensions.injectViewModel
import com.example.weather.main.CitiesViewModel
import kotlinx.android.synthetic.main.fragment_city_search.*
import javax.inject.Inject

class CitySearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_search, container, false)
    }

    override fun onAttach(context: Context) {
        WeatherApplication.appComponent.inject(this)
        citiesViewModel = injectViewModel(viewModelFactory)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(search_city_fragment) { _, insets ->
            val layoutParams = app_bar_search_city.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }

        city_search_recycler_view.layoutManager = LinearLayoutManager(context)
        city_search_recycler_view.adapter = FoundCityRecyclerViewAdapter(citiesViewModel.cities.value)
    }
}
package com.example.weather.city_management

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.adapters.CitiesRecyclerViewAdapter
import com.example.weather.application.WeatherApplication
import com.example.weather.db.City
import com.example.weather.extensions.injectViewModel
import com.example.weather.main.EventViewModel
import kotlinx.android.synthetic.main.fragment_city_management.*
import javax.inject.Inject

class CityManagementFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesManagementViewModel

    lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_management, container, false)
    }

    override fun onAttach(context: Context) {
        WeatherApplication.appComponent.inject(this)

        citiesViewModel = injectViewModel(viewModelFactory)
        eventViewModel = injectViewModel(viewModelFactory)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(city_management) { _, insets ->
            val layoutParams = app_bar_city_management.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }

        (activity as AppCompatActivity).setSupportActionBar(toolbar_city_management)
        toolbar_city_management.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_40)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        setHasOptionsMenu(true)

        city_management_frame.setOnClickListener {
            eventViewModel.state.value = EventViewModel.State.SEARCH_CITY
        }

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = CitiesRecyclerViewAdapter(citiesViewModel.cities.value)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.settings -> { true }
        android.R.id.home -> {
            eventViewModel.state.value = EventViewModel.State.CITY_WEATHER_FRAGMENT
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }
}
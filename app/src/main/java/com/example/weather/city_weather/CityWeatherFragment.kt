package com.example.weather.city_weather

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather.LocationProvider
import com.example.weather.R
import com.example.weather.adapters.StateAdapter
import com.example.weather.application.WeatherApplication
import com.example.weather.main.EventViewModel
import com.example.weather.db.City
import com.example.weather.extensions.injectViewModel
import com.example.weather.city_management.CitiesManagementViewModel
import com.example.weather.permissions.PermissionProvider
import kotlinx.android.synthetic.main.fragment_city_weather.*
import javax.inject.Inject


class CityWeatherFragment : Fragment() {

    @Inject
    lateinit var permissionProvider: PermissionProvider

    @Inject
    lateinit var locationProvider: LocationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesManagementViewModel

    lateinit var eventViewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_weather, container, false)
    }

    override fun onAttach(context: Context) {
        WeatherApplication.appComponent.inject(this)

        citiesViewModel = injectViewModel(viewModelFactory)
        eventViewModel = injectViewModel(viewModelFactory)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(content_container_city_weather) { _, insets ->
            val layoutParams = app_bar_city_weather.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }

        (activity as AppCompatActivity).setSupportActionBar(toolbar_city_weather)
        toolbar_city_weather.setNavigationIcon(R.drawable.ic_baseline_add_24)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        setHasOptionsMenu(true)

        citiesViewModel.addCity(City(1, "Tashkent"))

        citiesViewModel.cities.observe(viewLifecycleOwner, {
            viewPager.adapter = StateAdapter(requireActivity(), it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.settings -> { true }
        android.R.id.home -> {
            eventViewModel.state.value = EventViewModel.State.CITY_MANAGEMENT_FRAGMENT
            true
        }
        else -> { super.onOptionsItemSelected(item) }
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
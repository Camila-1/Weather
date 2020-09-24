package com.example.weather.city_management

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.extensions.either.applicativeError.handleError
import arrow.core.right
import com.example.weather.R
import com.example.weather.adapters.FoundCityRecyclerViewAdapter
import com.example.weather.application.WeatherApplication
import com.example.weather.extensions.injectViewModel
import kotlinx.android.synthetic.main.fragment_city_search.*
import kotlinx.coroutines.*
import javax.inject.Inject

class CitySearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var citiesViewModel: CitiesManagementViewModel


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

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(search_city_fragment) { _, insets ->
            val layoutParams = app_bar_search_city.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(0, insets.systemWindowInsetTop, 0, 0)
            insets.consumeSystemWindowInsets()
        }

        city_search_recycler_view.layoutManager = LinearLayoutManager(context)

        city_search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val foundCitiesWeather = runBlocking (Dispatchers.IO) {
                    citiesViewModel.findCitiesWeather(s.toString())
                }
                GlobalScope.launch(Dispatchers.Main) {
                    foundCitiesWeather.fold(
                        {
                            city_search_recycler_view.removeAllViewsInLayout()
                            nothing_found.setText(R.string.nothing_found)
                        },
                        {
                            nothing_found.text = ""
                            city_search_recycler_view.adapter = FoundCityRecyclerViewAdapter(it)
                        }
                    )
                }
            }
        })
    }
}
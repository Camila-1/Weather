package com.example.weather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.WeatherFragment
import com.example.weather.db.City

class StateAdapter(
    private val fragmentActivity: FragmentActivity,
    private val cities: List<City>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = cities.size

    override fun createFragment(position: Int): Fragment = WeatherFragment()
}

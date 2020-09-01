package com.example.weather.main

import androidx.lifecycle.LiveData
import com.example.weather.db.City

interface CitiesRepository {

    val cities: LiveData<List<City>>
}
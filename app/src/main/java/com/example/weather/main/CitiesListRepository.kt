package com.example.weather.main

import androidx.lifecycle.LiveData
import com.example.weather.db.CitiesDao
import com.example.weather.db.City

class CitiesListRepository(override val citiesDao: CitiesDao) : CitiesRepository {

    override val cities: LiveData<List<City>> = citiesDao.cities()



}

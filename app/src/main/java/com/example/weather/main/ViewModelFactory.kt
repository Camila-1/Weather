package com.example.weather.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.AppClass
import com.example.weather.db.WeatherDataBase

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitiesViewModel(CitiesListRepository(WeatherDataBase.database(AppClass.appContext())
            .cityDao())) as T
    }
}

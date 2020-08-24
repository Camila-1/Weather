package com.example.weather.network.response

import android.content.Context
import com.example.weather.LocationService
import com.example.weather.ServiceBuilder
import com.example.weather.SharedPreferenceHolder
import com.example.weather.network.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class WeatherRequest(val context: Context, val locationService: LocationService) {
    private val serviceBuilder = ServiceBuilder(context)
    private val weatherService = serviceBuilder.weatherService()

    suspend fun getWeatherResponseData(): WeatherResponse? {
        return withContext(Dispatchers.IO) {
            getWeatherResponse().execute().body()
        }
    }



    fun getWeatherResponse(): Call<WeatherResponse> {
        val unit = SharedPreferenceHolder(context).getUnit
        val lang = SharedPreferenceHolder(context).getLang


        return if (SharedPreferenceHolder(context).isGeolocationEnabled && locationService.hasLocationPermission()) {
            val coordinates = SharedPreferenceHolder(context).coordinates
            weatherService.getCurrentWeatherDataByCoordinates(coordinates.getValue("lat")!!,
                coordinates.getValue("lon")!!, unit, lang)

        } else weatherService.getCurrentWeatherDataByCityName(SharedPreferenceHolder(context).getCity!!, unit, lang)
    }
}
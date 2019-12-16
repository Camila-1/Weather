package com.example.weather

import android.content.Context
import com.example.weather.response.WeatherResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call

class WeatherRequest(val context: Context, val locationService: LocationService) {
    private val serviceBuilder = ServiceBuilder(context)
    private val weatherService = serviceBuilder.weatherService()

    suspend fun getWeatherResponseData(): WeatherResponse? {
        return GlobalScope.async {
            getWeatherResponse().execute().body()
        }.await()
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
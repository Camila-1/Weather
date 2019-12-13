package com.example.weather

import android.content.Context
import com.example.weather.response.WeatherResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call

class WeatherRequest(val context: Context) {
    private val serviceBuilder = ServiceBuilder(context)
    private val weatherService = serviceBuilder.weatherService()

    suspend fun getWeatherResponseData(): WeatherResponse? {
        return GlobalScope.async {
            getWeatherResponse().execute().body()
        }.await()
    }

    suspend fun getWeatherResponse(): Call<WeatherResponse> {
        val unit = SharedPreferenceHolder.getUnit(context)
        val lang = SharedPreferenceHolder.getLang(context)

//        if (SharedPreferenceHolder.isGeolocationEnabled(context)) {
//            val location = LocationService(context).getCurrentLocation()
//            return weatherService.getCurrentWeatherDataByCoordinates(location?.latitude.toString(),
//                location?.longitude.toString(), unit, lang)
//        }
        /*else*/ return weatherService.getCurrentWeatherDataByCityName(SharedPreferenceHolder.getCity(context), unit, lang)
    }
}
package com.example.weather

import android.annotation.SuppressLint
import android.app.Activity
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.work.Data
import com.example.weather.db.WeatherModel
import com.example.weather.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    private val context = appContext

    override fun doWork(): Result {
        val serviceBuilder = ServiceBuilder(context)
        val weatherService = serviceBuilder.weatherService()

        val response = getWeatherResponse(weatherService).execute()

        return if (response.isSuccessful && response.body() != null) {
            val db = WeatherModel(context)
            db.insert(response.body())
            Result.success()
        } else {
            Result.failure()
        }
    }

    private fun getWeatherResponse(service: WeatherService): Call<WeatherResponse> {
        val unit = inputData.getString("unit")!!
        val lang = inputData.getString("lang")!!

        if (inputData.getBoolean("isGeolocationEnabled", true)) {
            val location = getCurrentLocation()
            return service.getCurrentWeatherDataByCoordinates(location?.latitude.toString(),
                location?.longitude.toString(), unit, lang)
        }

        else return service.getCurrentWeatherDataByCityName(SharedPreferenceHolder.getCity(context), unit, lang)
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Location? {
        val location: Location?

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return location
    }


}
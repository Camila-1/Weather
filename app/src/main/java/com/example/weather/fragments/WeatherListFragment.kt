package com.example.weather.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.weather.*
import com.example.weather.response.WeatherResponse
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.android.synthetic.main.fragment_weather_list.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class WeatherListFragment : Fragment() {
    private val BASE_URL = "https://samples.openweathermap.org/data/2.5/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    var sharedPreferences = lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MyIntercwptor())
            .addInterceptor(ChuckerInterceptor(context!!))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val weatherService = ServiceBuilder.weatherService(retrofit)

        val response = getWeatherResponse(weatherService)

        response.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val adapter = Adapter(response.body()!!.list) {
                    (activity as MainActivity).itemClicked(it)
                }
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = adapter
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e("WeatherListFragment", t.toString(), t)
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun getWeatherResponse(service: WeatherService): Call<WeatherResponse> {
        if (!sharedPreferences.value.getBoolean("coordinates", true))
            return service.getCurrentWeatherDataByCityName(sharedPreferences.value.getString("city", "")!!)
        else {
            val location = getCurrentLocation()
            return service.getCurrentWeatherDataByCoordinates(location?.latitude.toString(), location?.longitude.toString())
        }
    }

    fun getCurrentLocation(): Location? {
        var location: Location? = null

        if (checkLocationPermission()) {
            val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }
        return location
    }

    fun checkLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity as MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(
                    activity as MainActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            else {
                ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            }
            return false
        }
        else return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}

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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.android.synthetic.main.fragment_weather_list.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger


class WeatherListFragment : Fragment() {
    private val BASE_URL = "https://samples.openweathermap.org/data/2.5/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

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
        val response = weatherService.getCurrentWeatherDataByCityName("London")
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

}

package com.example.weather.utils

import com.example.weather.network.weather_api.response.WeatherData
import com.example.weather.network.weather_api.response.WeatherResponse
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class DailyWeather(val min: Double, val max: Double, val icon: String)

class Weather (private val weatherResponse: WeatherResponse) {
    fun byDay(): List<Pair<LocalDate, List<WeatherData>>> {
        return weatherResponse.list.groupBy {
            Instant.ofEpochSecond(it.dateTime).atZone(ZoneId.systemDefault()).toLocalDate()
        }.toList()
    }

    fun minMaxByDay(pair: Pair<LocalDate, List<WeatherData>>): Pair<LocalDate, DailyWeather> {
        return pair.first to dailyWeather(pair.second)
    }

    private fun dailyWeather(list: List<WeatherData>): DailyWeather {
        return  DailyWeather(
            list.minOf { it.main.tempMin },
            list.maxOf { it.main.tempMax },
            list[0].weather[0].icon
        )
    }
}

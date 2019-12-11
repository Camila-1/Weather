package com.example.weather.db

import android.provider.BaseColumns

object Scheme {

    object WeatherData : BaseColumns {
        const val WEATHER_DATA_TABLE_NAME = "weather_data"
        const val ID = "id"
        const val CITY_ID = "city_id"
        const val DT = "dt"
        const val CLOUDS = "clouds"
        const val HUMIDITY = "humidity"
        const val PRESSURE = "pressure"
        const val TEMPERATURE = "temp"
        const val TEMPERATURE_MAX = "temp_max"
        const val TEMPERATURE_MIN = "temp_min"
        const val RAIN = "rain"
        const val SNOW = "snow"
        const val DESCRIPTION = "description"
        const val ICON = "icon"
        const val WIND_SPEED = "wind_speed"
        const val WIND_DEGREE = "wind_degree"
    }

    object City : BaseColumns {
        const val CITY_TABLE_NAME = "city"
        const val ID = "id"
        const val NAME = "city_name"
    }
}


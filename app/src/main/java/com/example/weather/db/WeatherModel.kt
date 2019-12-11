package com.example.weather.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.weather.response.WeatherResponse
import com.example.weather.db.Scheme.WeatherData
import com.example.weather.db.Scheme.City
import com.example.weather.response.Main
import com.example.weather.response.Weather
import com.example.weather.response.Wind

class WeatherModel(private val context: Context) {
    private val db = WeatherDBHelper(context).writableDatabase

    fun insert(response: WeatherResponse?) {
        if (response == null) return
        db.delete(WeatherData.WEATHER_DATA_TABLE_NAME, null, null)
        db.delete(City.CITY_TABLE_NAME, null, null)

        val city = ContentValues().apply {
            put(City.ID, response.city.id)
            put(City.NAME, response.city.name)
        }

        db.insert(City.CITY_TABLE_NAME, null, city)

        response.list.forEach {
            val weatherData = ContentValues().apply {
                put(WeatherData.CITY_ID, response.city.id)
                put(WeatherData.DT, it.dateTime)
                if (it.clouds?.get("all") != null)
                    put(WeatherData.CLOUDS, it.clouds["all"])
                put(WeatherData.HUMIDITY, it.main.humidity)
                put(WeatherData.PRESSURE, it.main.pressure)
                put(WeatherData.TEMPERATURE, it.main.temp)
                put(WeatherData.TEMPERATURE_MAX, it.main.tempMax)
                put(WeatherData.TEMPERATURE_MIN, it.main.tempMin)
                if (it.rain?.get("3h") != null)
                    put(WeatherData.RAIN, it.rain["3h"])
                if (it.snow?.get("3h") != null)
                    put(WeatherData.RAIN, it.snow["3h"])
                put(WeatherData.DESCRIPTION, it.weather[0].description)
                put(WeatherData.ICON, it.weather[0].icon)
                put(WeatherData.WIND_SPEED, it.wind.speed)
                put(WeatherData.WIND_DEGREE, it.wind.deg)
            }

            db.insert(WeatherData.WEATHER_DATA_TABLE_NAME, null, weatherData)
        }
    }

    fun getWeatherResponse(): WeatherResponse {
        val cityCursor =  initCursor(City.CITY_TABLE_NAME)
        val weatherDataCursor = initCursor(WeatherData.WEATHER_DATA_TABLE_NAME)
        val weatherDataList = mutableListOf<com.example.weather.response.WeatherData>()

        while (weatherDataCursor.moveToNext()) {
            weatherDataList.add(createWeatherData(weatherDataCursor))
        }

        val city = if (cityCursor.moveToNext()) {
            com.example.weather.response.City(
            id = cityCursor.getInt(cityCursor.getColumnIndex(City.ID)),
            name = cityCursor.getString(cityCursor.getColumnIndex(City.NAME))
            )
        } else com.example.weather.response.City(
            id = 0,
            name = ""
        )

        weatherDataCursor.close()
        cityCursor.close()

        return WeatherResponse(
            city = city,
            list = weatherDataList
        )
    }

    fun createWeatherData(cursor: Cursor): com.example.weather.response.WeatherData {
        return com.example.weather.response.WeatherData(
            dateTime = cursor.getLong(cursor.getColumnIndex(WeatherData.DT)),
            main = Main(
                temp = cursor.getDouble(cursor.getColumnIndex(WeatherData.TEMPERATURE)),
                tempMin = cursor.getDouble(cursor.getColumnIndex(WeatherData.TEMPERATURE_MIN)),
                tempMax = cursor.getDouble(cursor.getColumnIndex(WeatherData.TEMPERATURE_MAX)),
                pressure = cursor.getDouble(cursor.getColumnIndex(WeatherData.PRESSURE)),
                humidity = cursor.getDouble(cursor.getColumnIndex(WeatherData.HUMIDITY))
            ),
            weather = listOf(Weather(
                description = cursor.getString(cursor.getColumnIndex(WeatherData.DESCRIPTION)),
                icon = cursor.getString(cursor.getColumnIndex(WeatherData.ICON))
            )),
            rain = mapOf("3h" to cursor.getDouble(cursor.getColumnIndex(WeatherData.RAIN))),
            snow = mapOf("3h" to cursor.getDouble(cursor.getColumnIndex(WeatherData.SNOW))),
            wind = Wind(
                speed = cursor.getDouble(cursor.getColumnIndex(WeatherData.WIND_SPEED)),
                deg = cursor.getDouble(cursor.getColumnIndex(WeatherData.WIND_DEGREE))
            ),
            clouds = mapOf("all" to cursor.getDouble(cursor.getColumnIndex(WeatherData.CLOUDS)))
        )
    }

    fun initCursor(table: String): Cursor {
        val db = WeatherDBHelper(context).readableDatabase

        return db.query(table,
            null,
            null,
            null,
            null,
            null,
            null)
    }

}
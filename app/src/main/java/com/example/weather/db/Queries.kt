package com.example.weather.db

object Queries {
    internal const val CREATE_WEATHER_DATA_TABLE =
        "CREATE TABLE IF NOT EXISTS ${Scheme.WeatherData.WEATHER_DATA_TABLE_NAME} (" +
                "${Scheme.WeatherData.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Scheme.WeatherData.CITY_ID} INTEGER," +
                "${Scheme.WeatherData.DT} INTEGER," +
                "${Scheme.WeatherData.CLOUDS} INTEGER," +
                "${Scheme.WeatherData.HUMIDITY} INTEGER," +
                "${Scheme.WeatherData.PRESSURE} REAL," +
                "${Scheme.WeatherData.TEMPERATURE} REAL," +
                "${Scheme.WeatherData.TEMPERATURE_MAX} REAL," +
                "${Scheme.WeatherData.TEMPERATURE_MIN} REAL," +
                "${Scheme.WeatherData.RAIN} INTEGER," +
                "${Scheme.WeatherData.SNOW} INTEGER," +
                "${Scheme.WeatherData.DESCRIPTION} TEXT," +
                "${Scheme.WeatherData.ICON} TEXT," +
                "${Scheme.WeatherData.WIND_SPEED} REAL," +
                "${Scheme.WeatherData.WIND_DEGREE} REAL" +
                ")"

    internal const val CREATE_CITY_TABLE =
        "CREATE TABLE IF NOT EXISTS ${Scheme.City.CITY_TABLE_NAME} (" +
                "${Scheme.City.ID} INTEGER," +
                "${Scheme.City.NAME} TEXT" +
                ")"

    internal const val DELETE_TABLES =
        "DROP TABLE IF EXISTS ${Scheme.WeatherData.WEATHER_DATA_TABLE_NAME};" +
                "DROP TABLE IF EXISTS ${Scheme.City.CITY_TABLE_NAME}"
}
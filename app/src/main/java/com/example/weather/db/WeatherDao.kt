package com.example.weather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert
    fun insert(list: List<Weather>)

    @Query("SELECT * FROM weather WHERE city_id = :cityId")
    fun weather(cityId: Int): List<Weather>

}
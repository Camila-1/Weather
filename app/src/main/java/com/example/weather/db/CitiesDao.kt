package com.example.weather.db

import androidx.room.*

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(city: City)

    @Delete
    fun delete()

    @Query("SELECT * FROM cities")
    fun cities()
}
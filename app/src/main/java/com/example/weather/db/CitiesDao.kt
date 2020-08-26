package com.example.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

//    @Delete
//    suspend fun delete()

    @Query("SELECT * FROM cities")
    fun cities(): LiveData<List<City>>
}
package com.example.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Query("SELECT * FROM cities WHERE id = :id")
    fun findById(id : String): City?

    @Query("SELECT * FROM cities")
    fun cities(): LiveData<List<City>>
}
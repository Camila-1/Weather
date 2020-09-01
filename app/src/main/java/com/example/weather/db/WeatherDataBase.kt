package com.example.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.AppClass

@Database(entities = [City::class, Weather::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun cityDao(): CitiesDao

    abstract fun weatherDao(): WeatherDao

    companion object {
        private lateinit var INSTANCE: WeatherDataBase

        fun database(context: Context): WeatherDataBase {

            if (::INSTANCE.isInitialized) return INSTANCE

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WeatherDataBase::class.java,
                    "weather_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
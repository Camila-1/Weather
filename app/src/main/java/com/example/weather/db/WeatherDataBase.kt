package com.example.weather.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.AppClass

@Database(entities = [City::class, Weather::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun cityDao(): CitiesDao

    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: WeatherDataBase? = null

        fun database(): WeatherDataBase? {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    AppClass.appContext(),
                    WeatherDataBase::class.java,
                    "weather_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
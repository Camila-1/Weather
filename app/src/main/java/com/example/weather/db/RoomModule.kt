package com.example.weather.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(context: Context): WeatherDataBase {
        return Room
            .databaseBuilder(context, WeatherDataBase::class.java, "weather_database")
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesDao(weatherDataBase: WeatherDataBase): CitiesDao {
        return weatherDataBase.cityDao()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao {
        return weatherDataBase.weatherDao()
    }
}
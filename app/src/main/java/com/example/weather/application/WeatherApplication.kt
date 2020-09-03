package com.example.weather.application

import android.app.Application
import com.example.weather.di.ApplicationComponent
import com.example.weather.di.CoreModule
import com.example.weather.di.DaggerApplicationComponent


class WeatherApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
            private set
    }

    private val coreModule: CoreModule by lazy { CoreModule(this) }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .coreModule(coreModule)
            .build()
    }
}
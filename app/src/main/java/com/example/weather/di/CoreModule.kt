package com.example.weather.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}
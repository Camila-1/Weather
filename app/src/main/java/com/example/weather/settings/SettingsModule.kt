package com.example.weather.settings

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @Singleton
    fun provideSPHolder(context: Context): SharedPreferenceHolder {
        return SharedPreferenceHolder(context)
    }
}
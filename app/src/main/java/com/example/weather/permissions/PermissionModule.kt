package com.example.weather.permissions

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class PermissionModule {

    @Provides
    fun provideAccessFineLocationPermission(context: Context): AccessFineLocationPermission {
        return AccessFineLocationPermission(context)
    }

    @Provides
    fun provideCallPhonePermission(context: Context): CallPhonePermission {
        return CallPhonePermission(context)
    }
}
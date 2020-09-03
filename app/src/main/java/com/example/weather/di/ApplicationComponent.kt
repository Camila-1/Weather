package com.example.weather.di

import android.app.Activity
import android.app.Application
import com.example.weather.db.RoomModule
import com.example.weather.main.MainActivity
import com.example.weather.main.ViewModelModule
import com.example.weather.network.NetworkModule
import com.example.weather.permissions.PermissionModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    CoreModule::class,
    PermissionModule::class,
    ViewModelModule::class,
    RoomModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder

        fun coreModule(coreModule: CoreModule): Builder
    }

    fun inject(activity: MainActivity)
}

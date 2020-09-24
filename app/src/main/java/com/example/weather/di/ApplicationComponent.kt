package com.example.weather.di

import android.app.Application
import com.example.weather.city_management.CityManagementFragment
import com.example.weather.city_management.CitySearchFragment
import com.example.weather.city_weather.CityWeatherFragment
import com.example.weather.db.RoomModule
import com.example.weather.main.MainActivity
import com.example.weather.network.NetworkModule
import com.example.weather.permissions.PermissionModule
import com.example.weather.settings.SettingsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    CoreModule::class,
    PermissionModule::class,
    ViewModelModule::class,
    RoomModule::class,
    SettingsModule::class
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
    fun inject(fragment: CityManagementFragment)
    fun inject(fragment: CityWeatherFragment)
    fun inject(searchFragment: CitySearchFragment)
}

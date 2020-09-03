package com.example.weather.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.db.CitiesDao
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module(includes = [RepositoryModule::class])
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @ViewModelKey(CitiesViewModel::class)
    @IntoMap
    internal abstract fun bindCitiesViewModel(citiesViewModel: CitiesViewModel): ViewModel
}

@Module
class RepositoryModule {

    @Provides
    fun provideCitiesRepository(citiesDao: CitiesDao): CitiesRepository {
        return CitiesListRepository(citiesDao)
    }
}

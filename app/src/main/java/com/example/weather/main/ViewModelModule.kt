package com.example.weather.main

import androidx.lifecycle.ViewModelProvider
import com.example.weather.db.CitiesDao
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass
import androidx.lifecycle.ViewModel as ViewModel1

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel1>)

@Module(includes = [RepositoryModule::class])
class ViewModelModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        providers: MutableMap<Class<out ViewModel1>, @JvmSuppressWildcards Provider<ViewModel1>>
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel1?> create(modelClass: Class<T>): T {
                return requireNotNull(providers[modelClass as Class<out ViewModel1>]).get() as T
            }
        }
    }

    @Provides
    @ViewModelKey(CitiesViewModel::class)
    @IntoMap
    fun provideCitiesViewModel(citiesRepository: CitiesRepository): CitiesViewModel {
        return CitiesViewModel(citiesRepository)
    }
}

@Module
class RepositoryModule {

    @Provides
    fun provideCitiesRepository(citiesDao: CitiesDao): CitiesRepository {
        return CitiesListRepository(citiesDao)
    }
}

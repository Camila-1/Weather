package com.example.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.city_management.CitiesManagementViewModel
import com.example.weather.main.EventViewModel
import com.example.weather.city_management.ManagementRepository
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module(includes = [RepositoryModule::class])
class ViewModelModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        providers: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
            }
        }
    }

    @Provides
    @IntoMap
    @ViewModelKey(CitiesManagementViewModel::class)
    fun provideCitiesViewModel(citiesRepository: ManagementRepository): ViewModel {
        return CitiesManagementViewModel(citiesRepository)
    }

    @Provides
    @Singleton
    @IntoMap
    @ViewModelKey(EventViewModel::class)
    fun provideEventViewModel(): ViewModel {
        return EventViewModel()
    }
}

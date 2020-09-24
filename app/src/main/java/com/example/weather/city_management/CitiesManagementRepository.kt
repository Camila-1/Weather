package com.example.weather.city_management

import android.util.Log
import androidx.lifecycle.LiveData
import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import com.example.weather.db.CitiesDao
import com.example.weather.db.City
import com.example.weather.network.google_api.GoogleApiService
import com.example.weather.network.google_api.response.Variant
import com.example.weather.network.weather_api.WeatherApiService
import com.example.weather.network.weather_api.response.WeatherResponse
import com.example.weather.settings.SharedPreferenceHolder
import com.example.weather.utils.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*

class CitiesManagementRepository(
    override val citiesDao: CitiesDao,
    private val googleApiService: GoogleApiService,
    private val weatherApiService: WeatherApiService,
    private val spHolder: SharedPreferenceHolder
) : ManagementRepository {

    override val cities: LiveData<List<City>> = citiesDao.cities()

    @ExperimentalCoroutinesApi
    override suspend fun autocompleteCitiesWeather(string: String):
            Either<CityManagementError, Nel<Pair<Variant, Optional<Weather>>>> {
        val cities = autocomplete(string)
        return cities.flatMap { citiesWeather(it) }
    }

    private suspend fun autocomplete(string: String): Either<CityManagementError, Nel<Variant>> {
        return withContext(Dispatchers.IO) {
            Either.catch {
                googleApiService.findCities(
                    "ru",
                    "(cities)",
                    string,
                    UUID.randomUUID().toString()
                )
            }.mapLeft {
                Log.e(this@CitiesManagementRepository::class.toString(), it.message, it)
                CityNotFoundError
            }.flatMap {
                Nel.fromList(it.predictions).toEither { CityNotFoundError }
            }
        }
    }

    private suspend fun cityWeather(city: Variant):
            Either<CityManagementError, Pair<Variant, Optional<Weather>>> {
        val weatherResponse = withContext(Dispatchers.IO) {
            Either.catch {
                weatherApiService.getCurrentWeatherDataByCityName(
                    city.description,
                    spHolder.getUnit,
                    spHolder.getLang
                ).let { Optional.of(it) }
            }
        }

        return weatherResponse.fold(
            {
                Log.e(this@CitiesManagementRepository::class.toString(), it.message, it)
                when (it) {
                    is HttpException -> {
                        if (it.code() == 404) Optional.empty<Weather>().right()
                        else WeatherApiError.left()
                    }
                    else -> WeatherApiError.left()
                }
            },
            { maybeWeather -> maybeWeather.map { Weather(it) }.right() }
        ).map { city to it }
    }

    private suspend fun citiesWeather(cities: Nel<Variant>):
            Either<CityManagementError, Nel<Pair<Variant, Optional<Weather>>>> {
        return cities.map { cityWeather(it) }.sequence(Either.applicative()).fix()
    }
}

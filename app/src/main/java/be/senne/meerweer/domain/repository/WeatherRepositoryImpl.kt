package be.senne.meerweer.domain.repository

import android.util.Log
import be.senne.meerweer.data.local.WeatherDao
import be.senne.meerweer.data.remote.GeocodingService
import be.senne.meerweer.data.remote.WeatherService
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val geocodingService: GeocodingService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    init {
        Log.wtf("", "WeatherRepository, $weatherService, $geocodingService, $weatherDao")
    }

    override suspend fun searchLocations(name: String): Result<List<WeatherLocation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedLocations(): Result<List<WeatherLocation>> {
        delay(1000)
        return Result.success(listOf(
            WeatherLocation("Location 1", 0.0, 0.0, 0),
            WeatherLocation("Location 2", 0.0, 0.0, 0),
            WeatherLocation("Location 3", 0.0, 0.0, 0)
        ))
        TODO("Not yet implemented")
    }

    override suspend fun saveLocation(weatherLocation: WeatherLocation): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(weatherLocation: WeatherLocation): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherData(weatherLocation: WeatherLocation): Result<WeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWeatherData(): Result<List<WeatherData>> {
        TODO("Not yet implemented")
    }


}
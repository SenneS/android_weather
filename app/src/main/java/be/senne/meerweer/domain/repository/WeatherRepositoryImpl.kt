package be.senne.meerweer.domain.repository

import android.util.Log
import be.senne.meerweer.data.local.WeatherDao
import be.senne.meerweer.data.remote.GeocodingService
import be.senne.meerweer.data.remote.WeatherService
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import kotlinx.coroutines.delay
import java.io.IOException
import java.util.UUID
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
        val searchResponse = geocodingService.searchLocation(name)
        if(searchResponse.results == null || searchResponse.error != null) {
            return Result.failure(IOException("API Failure"))
        }
        return Result.success(searchResponse.results.map {
            WeatherLocation(UUID.randomUUID(), it.name, it.latitude, it.longitude, it.elevation)
        })
    }

    override suspend fun getSavedLocations(): Result<List<WeatherLocation>> {
        delay(1000)
        return Result.success(listOf(
            WeatherLocation(UUID.randomUUID(),"Location 1", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(),"Location 2", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(),"Location 3", 0.0, 0.0, 0)
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
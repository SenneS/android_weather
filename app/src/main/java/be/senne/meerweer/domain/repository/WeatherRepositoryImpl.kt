package be.senne.meerweer.domain.repository

import android.util.Log
import be.senne.meerweer.data.local.WeatherDao
import be.senne.meerweer.data.local.WeatherDataEntity
import be.senne.meerweer.data.local.WeatherLocationEntity
import be.senne.meerweer.data.remote.GeocodingService
import be.senne.meerweer.data.remote.WeatherService
import be.senne.meerweer.data.remote.dto.ForecastResponse
import be.senne.meerweer.data.remote.dto.GeocodingGetResponse
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getSavedLocations(): List<WeatherLocation> {
        delay(1000)
        return listOf(
            WeatherLocation(UUID.randomUUID(),"Location 1", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(),"Location 2", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(),"Location 3", 0.0, 0.0, 0)
        )
        TODO("Not yet implemented")
    }

    override suspend fun saveLocation(weatherLocation: WeatherLocation): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(weatherLocation: WeatherLocation): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCachedWeatherData() : List<WeatherData> {
        val cachedData = weatherDao.getAllWeatherData()
        val validData = cachedData.filter {
            filterExpiredWeatherData(it)
        }.map {
            mapLocalWeatherDataToDomain(it)
        }.sortedBy {
            it.name
        }
        return validData
    }

    override suspend fun getMissingWeatherData() : List<WeatherLocation> {
        val savedLocations = weatherDao.getWeatherLocations()
        val cachedData = weatherDao.getAllWeatherData()

        val missingLocations = savedLocations.filter {
            filterValidWeatherData(it, cachedData)
        }.map {
            mapLocalWeatherLocationToDomain(it)
        }.sortedBy {
            it.name
        }

        return missingLocations
    }

    override suspend fun updateWeatherData(location : WeatherLocation) : Result<WeatherData> {
        return Result.runCatching {
            val weather = weatherService.getForecast(location.latitude, location.longitude)
            val localWeather = mapRemoteWeatherDataToLocal(weather)
            weatherDao.insertWeatherDatas(localWeather)

            val domainWeather = mapLocalWeatherDataToDomain(localWeather)
            return@runCatching domainWeather
        }
    }



    companion object {
        private fun isTimestampValid(stamp : Long) : Boolean {
            //Filter CachedData older than 5 Minutes
            val currentStamp = System.currentTimeMillis()
            val cacheThreshold = 5 * 60 * 1000
            val delta = currentStamp - stamp
            return delta in 0..<cacheThreshold
        }
        private fun filterExpiredWeatherData(data : WeatherDataEntity) : Boolean {
            return isTimestampValid(data.timestamp)
        }

        private fun filterValidWeatherData(location : WeatherLocationEntity, cachedData : List<WeatherDataEntity>) : Boolean {
            return cachedData.none {
                if(it.id != location.id) {
                    return@none false
                }
                return@none !isTimestampValid(it.timestamp)
            }
        }

        private fun mapLocalWeatherLocationToDomain(data : WeatherLocationEntity) : WeatherLocation {
            TODO()
        }

        private fun mapLocalWeatherDataToDomain(data : WeatherDataEntity) : WeatherData {
            TODO()
        }

        private fun mapRemoteWeatherLocationToLocal(data : GeocodingGetResponse) : WeatherLocationEntity {
            TODO()
        }

        private fun mapRemoteWeatherDataToLocal(data : ForecastResponse) : WeatherDataEntity {
            TODO()
        }
    }

}
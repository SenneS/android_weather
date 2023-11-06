package be.senne.meerweer.domain.repository

import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation

interface WeatherRepository {
    suspend fun searchLocations(name: String) : Result<List<WeatherLocation>>
    suspend fun getSavedLocations() : List<WeatherLocation>
    suspend fun saveLocation(weatherLocation: WeatherLocation) : Result<Unit>
    suspend fun deleteLocation(weatherLocation: WeatherLocation) : Result<Unit>
    suspend fun getCachedWeatherData() : List<WeatherData>
    suspend fun getMissingWeatherData() : List<WeatherLocation>
    suspend fun updateWeatherData(location : WeatherLocation) : Result<WeatherData>
}
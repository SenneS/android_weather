package be.senne.meerweer.domain.repository

import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation

interface WeatherRepository {
    suspend fun searchLocations(name: String) : Result<List<WeatherLocation>>
    suspend fun getSavedLocations() : Result<List<WeatherLocation>>
    suspend fun saveLocation(weatherLocation: WeatherLocation) : Result<Unit>
    suspend fun deleteLocation(weatherLocation: WeatherLocation) : Result<Unit>
    suspend fun getWeatherData(weatherLocation: WeatherLocation) : Result<WeatherData>
    suspend fun getAllWeatherData() : Result<List<WeatherData>>
}
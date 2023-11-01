package be.senne.meerweer.data.repository

import be.senne.meerweer.data.remote.WeatherService

class WeatherRepository(private val service : WeatherService) {
    suspend fun getForecast() = service.
}
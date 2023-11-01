package be.senne.meerweer.data.remote

import be.senne.meerweer.data.remote.dto.ForecastResponse
import retrofit2.http.GET

interface WeatherService {
    companion object {
        const val BASE_URL = "https://api.open-meteo.com/v1"
    }

    @GET("$BASE_URL/forecast")
    suspend fun getForecast() : ForecastResponse
}
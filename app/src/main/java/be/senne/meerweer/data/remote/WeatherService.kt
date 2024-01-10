package be.senne.meerweer.data.remote

import be.senne.meerweer.data.remote.dto.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    companion object {
        const val BASE_URL = "https://api.open-meteo.com/v1"
    }

    @GET("$BASE_URL/forecast?current=temperature_2m,precipitation,weather_code,wind_speed_10m,wind_direction_10m,wind_gusts_10m&hourly=temperature_2m,precipitation_probability,weather_code&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant&timezone=auto")
    suspend fun getForecast(@Query("latitude") latitude : Double, @Query("longitude") longitude: Double) : ForecastResponse
}
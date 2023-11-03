package be.senne.meerweer.domain.model

import java.time.ZonedDateTime

data class WeatherDayData(
    val time: ZonedDateTime,
    val weatherCode: WeatherCode,
    val maxTemperature: Double,
    val minTemperature: Double,
    val sunrise: ZonedDateTime,
    val sunset: ZonedDateTime,
    val maxPrecipitation: Double,
    val maxWindspeed: Double,
    val maxWindgusts: Double,
    val windDirection: WeatherWindDirection
)

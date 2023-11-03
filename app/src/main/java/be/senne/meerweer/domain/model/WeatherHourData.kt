package be.senne.meerweer.domain.model

import java.time.ZonedDateTime

data class WeatherHourData(
    val time: ZonedDateTime,
    val weatherCode: WeatherCode,
    val temperature: Double,
    val precipitation: Double,
    val windspeed: Double,
    val windgusts: Double,
    val windDirection: WeatherWindDirection
)

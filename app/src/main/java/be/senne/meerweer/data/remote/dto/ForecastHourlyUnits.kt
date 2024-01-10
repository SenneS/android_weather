package be.senne.meerweer.data.remote.dto

data class ForecastHourlyUnits(
    val precipitation_probability: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String,
)
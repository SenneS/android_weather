package be.senne.meerweer.data.remote.dto

data class ForecastCurrentUnits(
    val time : String,
    val interval : String,
    val temperature_2m : String,
    val precipitation : String,
    val weather_code : String,
    val wind_speed_10m : String,
    val wind_direction_10m : String,
    val wind_gusts_10m : String
)

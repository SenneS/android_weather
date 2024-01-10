package be.senne.meerweer.data.remote.dto

data class ForecastCurrent(
    val time : String,
    val temperature_2m : Double,
    val precipitation : Double,
    val weather_code : Int,
    val wind_speed_10m : Double,
    val wind_direction_10m : Double,
    val wind_gusts_10m : Double,
)

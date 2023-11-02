package be.senne.meerweer.data.remote.dto

data class ForecastHourlyUnits(
    val precipitation_probability: String,
    val relativehumidity_2m: String,
    val temperature_2m: String,
    val time: String,
    val visibility: String,
    val weathercode: String,
    val winddirection_10m: String,
    val windgusts_10m: String,
    val windspeed_10m: String
)
package be.senne.meerweer.data.remote.dto

data class ForecastHourly(
    val precipitation_probability: List<Int>,
    val relativehumidity_2m: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val visibility: List<Double>,
    val weathercode: List<Int>,
    val winddirection_10m: List<Int>,
    val windgusts_10m: List<Double>,
    val windspeed_10m: List<Double>
)
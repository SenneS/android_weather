package be.senne.meerweer.data.remote.dto

data class ForecastHourly(
    val precipitation_probability: List<Double>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>
)
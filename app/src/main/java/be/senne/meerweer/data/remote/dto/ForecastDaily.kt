package be.senne.meerweer.data.remote.dto

data class ForecastDaily(
    val precipitation_probability_max: List<Int>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>,
    val winddirection_10m_dominant: List<Int>,
    val windgusts_10m_max: List<Double>,
    val windspeed_10m_max: List<Double>
)
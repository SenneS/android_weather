package be.senne.meerweer.data.remote.dto

data class ForecastDaily(
    val precipitation_probability_max: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weather_code: List<Int>,
    val wind_direction_10m_dominant: List<Double>,
    val wind_gusts_10m_max: List<Double>,
    val wind_speed_10m_max: List<Double>
)
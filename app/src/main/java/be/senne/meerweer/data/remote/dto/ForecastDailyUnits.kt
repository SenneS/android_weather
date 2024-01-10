package be.senne.meerweer.data.remote.dto

data class ForecastDailyUnits(
    val precipitation_probability_max: String,
    val sunrise: String,
    val sunset: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val weather_code: String,
    val wind_direction_10m_dominant: String,
    val wind_gusts_10m_max: String,
    val wind_speed_10m_max: String
)
package be.senne.meerweer.data.remote.dto

data class ForecastDailyUnits(
    val precipitation_probability_max: String,
    val sunrise: String,
    val sunset: String,
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val weathercode: String,
    val winddirection_10m_dominant: String,
    val windgusts_10m_max: String,
    val windspeed_10m_max: String
)
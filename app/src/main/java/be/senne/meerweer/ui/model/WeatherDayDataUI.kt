package be.senne.meerweer.ui.model

data class WeatherDayDataUI(
    val day: String,
    val minTemperature : String,
    val maxTemperature: String,
    val precipitation: String,
    val maxWind: String,
    val maxGusts: String,
    val windDirection: String,
    val icon: String
)
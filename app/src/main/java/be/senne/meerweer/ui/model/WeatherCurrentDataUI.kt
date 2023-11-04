package be.senne.meerweer.ui.model

data class WeatherCurrentDataUI(
    val temperature: String = "",
    val wind: String = "",
    val gusts: String = "",
    val windDirection: String = "",
    val precipitation: String = "",
    val sunrise: String = "",
    val sunset: String = "",
)

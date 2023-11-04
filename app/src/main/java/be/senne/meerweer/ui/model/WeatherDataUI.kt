package be.senne.meerweer.ui.model

data class WeatherDataUI(
    val location: String,
    val now: WeatherCurrentDataUI,
    val hourly : List<WeatherHourDataUI>,
    val daily: List<WeatherDayDataUI>
)

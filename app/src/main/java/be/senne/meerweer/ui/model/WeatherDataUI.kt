package be.senne.meerweer.ui.model

import java.util.UUID

data class WeatherDataUI(
    val locationUuid: UUID,
    val location: String,
    var now: WeatherCurrentDataUI = WeatherCurrentDataUI(),
    var hourly : List<WeatherHourDataUI> = emptyList(),
    var daily: List<WeatherDayDataUI> = emptyList()
)

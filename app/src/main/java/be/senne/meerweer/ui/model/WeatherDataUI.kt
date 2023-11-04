package be.senne.meerweer.ui.model

import java.time.Instant
import java.util.UUID

data class WeatherDataUI(
    val locationUuid: UUID,
    val timestamp: Instant = Instant.EPOCH,
    val location: String,
    var now: WeatherCurrentDataUI = WeatherCurrentDataUI(),
    var hourly : List<WeatherHourDataUI> = emptyList(),
    var daily: List<WeatherDayDataUI> = emptyList()
)

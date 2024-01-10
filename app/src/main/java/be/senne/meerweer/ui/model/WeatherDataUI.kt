package be.senne.meerweer.ui.model

import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherData
import java.time.Instant
import java.util.UUID

data class WeatherDataUI(
    val timestamp: Long,
    val location: String,
    var now: WeatherCurrentDataUI,
    var hourly : List<WeatherHourDataUI> = emptyList(),
    var daily: List<WeatherDayDataUI> = emptyList()
)
{
    companion object {
        fun fromWeatherData(data : WeatherData, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : WeatherDataUI {
            return WeatherDataUI(
                data.timestamp,
                data.name,
                WeatherCurrentDataUI.fromWeatherData(data, speedUnit, tempUnit, precipUnit),
                WeatherHourDataUI.fromWeatherHourDatas(data.hourlyData, speedUnit, tempUnit, precipUnit),
                WeatherDayDataUI.fromWeatherDayDatas(data.dailyData, speedUnit, tempUnit, precipUnit)
            )
        }
    }
}
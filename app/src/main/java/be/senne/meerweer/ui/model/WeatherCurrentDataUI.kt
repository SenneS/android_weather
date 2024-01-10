package be.senne.meerweer.ui.model

import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.utils.formatPrecipitation
import be.senne.meerweer.utils.formatTemperature
import be.senne.meerweer.utils.formatToHHmm
import be.senne.meerweer.utils.formatVelocity

data class WeatherCurrentDataUI(
    val temperature: String = "",
    val wind: String = "",
    val gusts: String = "",
    val windDirectionLongText: Int,
    val windDirectionShortText: Int,
    val precipitation: String = "",
    val sunrise: String = "",
    val sunset: String = "",
    val weatherIcon: Int,
    val weatherText: Int
)
{
    companion object {
        fun fromWeatherData(data : WeatherData, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : WeatherCurrentDataUI {
            return WeatherCurrentDataUI(
                formatTemperature(data.temperature, tempUnit),
                formatVelocity(data.windspeed, speedUnit),
                formatVelocity(data.windgusts, speedUnit),
                data.windDirection.long_text,
                data.windDirection.short_text,
                formatPrecipitation(data.precipitation, precipUnit),
                data.dailyData[0].sunrise.formatToHHmm(),
                data.dailyData[0].sunset.formatToHHmm(),
                data.weatherCode.to_icon(),
                data.weatherCode.to_str()
            )
        }
    }
}
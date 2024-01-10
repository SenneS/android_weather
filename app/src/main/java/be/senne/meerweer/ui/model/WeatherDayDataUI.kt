package be.senne.meerweer.ui.model

import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherDayData
import be.senne.meerweer.utils.formatPrecipitation
import be.senne.meerweer.utils.formatTemperature
import be.senne.meerweer.utils.formatVelocity

data class WeatherDayDataUI(
    val day: String,
    val minTemperature : String,
    val maxTemperature: String,
    val precipitation: String,
    val maxWind: String,
    val maxGusts: String,
    val windDirectionLongText: Int,
    val windDirectionShortText: Int,
    val weatherText : Int,
    val weatherIcon: Int
)
{
    companion object {
        fun fromWeatherDayData(data : WeatherDayData, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : WeatherDayDataUI {
            return WeatherDayDataUI(
                data.time.dayOfWeek.name,
                formatTemperature(data.minTemperature, tempUnit),
                formatTemperature(data.maxTemperature, tempUnit),
                String.format("%.2f%%", data.maxPrecipitation),
                formatVelocity(data.maxWindspeed, speedUnit),
                formatVelocity(data.maxWindgusts, speedUnit),
                data.windDirection.long_text,
                data.windDirection.short_text,
                data.weatherCode.to_str(),
                data.weatherCode.to_icon()
            )
        }
        fun fromWeatherDayDatas(data : List<WeatherDayData>, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : List<WeatherDayDataUI> {
            return data.map {
                fromWeatherDayData(it, speedUnit, tempUnit, precipUnit)
            };
        }
    }
}
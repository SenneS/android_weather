package be.senne.meerweer.ui.model

import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherHourData
import be.senne.meerweer.utils.formatTemperature

data class WeatherHourDataUI(
    val hour: String,
    val precipitation: String,
    val temperature: String,
    val weatherText : Int,
    val weatherIcon: Int
)
{
    companion object {
        fun fromWeatherHourData(data : WeatherHourData, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : WeatherHourDataUI {
            return WeatherHourDataUI(
                "${data.time.hour.toString().padStart(2, '0')}:00",
                String.format("%.2f%%", data.precipitation),
                formatTemperature(data.temperature, tempUnit),
                data.weatherCode.to_str(),
                data.weatherCode.to_icon()
            )
        }
        fun fromWeatherHourDatas(data : List<WeatherHourData>, speedUnit : MeasurementUnit, tempUnit : MeasurementUnit, precipUnit : MeasurementUnit) : List<WeatherHourDataUI> {
            return data.map {
                fromWeatherHourData(it, speedUnit, tempUnit, precipUnit)
            };
        }
    }
}
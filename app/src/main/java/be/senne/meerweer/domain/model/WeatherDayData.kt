package be.senne.meerweer.domain.model

import be.senne.meerweer.data.local.WeatherDayDataEntity
import be.senne.meerweer.data.remote.dto.ForecastDaily
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class WeatherDayData(
    val time: ZonedDateTime,
    val weatherCode: WeatherCode,
    val maxTemperature: Double,
    val minTemperature: Double,
    val sunrise: ZonedDateTime,
    val sunset: ZonedDateTime,
    val maxPrecipitation: Double,
    val maxWindspeed: Double,
    val maxWindgusts: Double,
    val windDirection: WeatherWindDirection
) {
    companion object {
        fun mapLocalToDomain(entity : WeatherDayDataEntity) : WeatherDayData {
            return WeatherDayData(
                ZonedDateTime.parse(entity.time),
                entity.weatherCode,
                entity.maxTemperature,
                entity.minTemperature,
                ZonedDateTime.parse(entity.sunrise),
                ZonedDateTime.parse(entity.sunset),
                entity.maxPrecipitation,
                entity.maxWindspeed,
                entity.maxWindgusts,
                entity.windDirection
            );
        }

        fun mapDomainToLocal(domain : WeatherDayData, locationId : Long) : WeatherDayDataEntity {
            return WeatherDayDataEntity(
                null,
                locationId,
                domain.time.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                domain.sunrise.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                domain.sunset.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                domain.maxPrecipitation,
                domain.maxWindspeed,
                domain.maxWindgusts,
                domain.minTemperature,
                domain.maxTemperature,
                domain.weatherCode,
                domain.windDirection
            )
        }

        fun mapRemoteToDomain(remote : ForecastDaily, timezone : String) : List<WeatherDayData> {
            return (0..6).map {
                WeatherDayData(
                    ZonedDateTime.parse("${remote.time[it]}T00:00${timezone}"),
                    WeatherCode.from_ww(remote.weather_code[it]),
                    remote.temperature_2m_max[it],
                    remote.temperature_2m_min[it],
                    ZonedDateTime.parse("${remote.sunrise[it]}${timezone}"),
                    ZonedDateTime.parse("${remote.sunset[it]}${timezone}"),
                    remote.precipitation_probability_max[it],
                    remote.wind_speed_10m_max[it],
                    remote.wind_gusts_10m_max[it],
                    WeatherWindDirection.fromDegrees(remote.wind_direction_10m_dominant[it]),
                )
            }
        }
    }
}

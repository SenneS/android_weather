package be.senne.meerweer.domain.model

import be.senne.meerweer.data.local.WeatherContainerEntity
import be.senne.meerweer.data.local.WeatherDataEntity
import be.senne.meerweer.data.remote.dto.ForecastResponse
import kotlin.math.abs

data class WeatherData(
    val locationId : Long = 0,
    val timestamp: Long,
    val name : String ="",

    val weatherCode: WeatherCode = WeatherCode.CLEAR_SKY,
    val temperature: Double = 0.0,
    val precipitation: Double = 0.0,
    val windspeed: Double = 0.0,
    val windgusts: Double = 0.0,
    val windDirection: WeatherWindDirection = WeatherWindDirection.SOUTH,

    val hourlyData: List<WeatherHourData> = emptyList(),
    val dailyData: List<WeatherDayData> = emptyList()
)
{
    companion object {
        fun mapLocalToDomain(entity : WeatherDataEntity) : WeatherData {

            val dailyData = entity.dailyData.map { WeatherDayData.mapLocalToDomain(it) }
            val hourlyData = entity.hourlyData.map { WeatherHourData.mapLocalToDomain(it) }


            return WeatherData(
                entity.wd.locationId,
                entity.wd.timestamp,
                entity.wd.name,
                entity.wd.weatherCode,
                entity.wd.temperature,
                entity.wd.precipitation,
                entity.wd.windSpeed,
                entity.wd.windGusts,
                entity.wd.windDirection,
                hourlyData,
                dailyData
            )
        }
        fun mapDomainToLocal(domain : WeatherData) : WeatherDataEntity {
            val container = WeatherContainerEntity(
                domain.locationId,
                domain.timestamp,
                domain.name,
                domain.temperature,
                domain.precipitation,
                domain.weatherCode,
                domain.windspeed,
                domain.windDirection,
                domain.windgusts,
            )

            val dailyData = domain.dailyData.map { WeatherDayData.mapDomainToLocal(it, domain.locationId) };
            val hourlyData = domain.hourlyData.map { WeatherHourData.mapDomainToLocal(it, domain.locationId) };

            return WeatherDataEntity(
                container,
                hourlyData,
                dailyData
            )
        }
        fun mapRemoteToDomain(remote : ForecastResponse, locationId : Long, locationName : String) : WeatherData {

            var timezone_hours = remote.utc_offset_seconds!! / 3600
            var timezone_minutes = (remote.utc_offset_seconds % 3600) / 60
            var timezone_sign = "+"
            if(timezone_hours < 0) {
                timezone_hours = -timezone_hours
                timezone_sign = "-"
            }
            if(timezone_minutes < 0) {
                timezone_minutes = -timezone_minutes
            }


            val timezone = "${timezone_sign}${timezone_hours.toString().padStart(2, '0')}:${timezone_minutes.toString().padStart(2, '0')}[${remote.timezone}]"

            val hourlyData = WeatherHourData.mapRemoteToDomain(remote.hourly!!, timezone);
            val dailyData = WeatherDayData.mapRemoteToDomain(remote.daily!!, timezone)

            val current = remote.current!!;

            return WeatherData(
                locationId,
                System.currentTimeMillis(),
                locationName,
                WeatherCode.from_ww(current.weather_code),
                current.temperature_2m,
                current.precipitation,
                current.wind_speed_10m,
                current.wind_gusts_10m,
                WeatherWindDirection.fromDegrees(current.wind_direction_10m),
                hourlyData,
                dailyData
            )

        }
    }
}
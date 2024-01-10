package be.senne.meerweer.domain.model

import be.senne.meerweer.data.local.WeatherHourDataEntity
import be.senne.meerweer.data.remote.dto.ForecastHourly
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class WeatherHourData(
    val time: ZonedDateTime,
    val weatherCode: WeatherCode,
    val temperature: Double,
    val precipitation: Double
)
{
    companion object {
        fun mapLocalToDomain(entity : WeatherHourDataEntity) : WeatherHourData {
            return WeatherHourData(
                ZonedDateTime.parse(entity.time),
                entity.weatherCode,
                entity.temperature,
                entity.maxPrecipitation
            );
        }

        fun mapDomainToLocal(domain : WeatherHourData, locationId : Long) : WeatherHourDataEntity {
            return WeatherHourDataEntity(
                null,
                locationId,
                domain.time.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                domain.precipitation,
                domain.temperature,
                domain.weatherCode,
            )
        }

        fun mapRemoteToDomain(remote : ForecastHourly, timezone : String) : List<WeatherHourData> {
            return (0..23).map {
                WeatherHourData(
                    ZonedDateTime.parse("${remote.time[it]}${timezone}"),
                    WeatherCode.from_ww(remote.weather_code[it]),
                    remote.temperature_2m[it],
                    remote.precipitation_probability[it]
                )
            }
        }
    }
}

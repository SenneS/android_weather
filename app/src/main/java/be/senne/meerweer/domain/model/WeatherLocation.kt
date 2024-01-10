package be.senne.meerweer.domain.model

import be.senne.meerweer.data.local.WeatherLocationEntity
import be.senne.meerweer.data.remote.dto.GeocodingSearchItem

data class WeatherLocation(
    val id: Long,
    val name : String,
    val extra : String,
    val country : CountryCode,
    val latitude : Double,
    val longitude : Double
) {
    companion object {
        fun mapLocalToDomain(entity : WeatherLocationEntity) : WeatherLocation {
            return WeatherLocation(
                entity.id,
                entity.name,
                entity.extra,
                entity.country,
                entity.latitude,
                entity.longitude
            )
        }

        fun mapDomainToLocal(domain : WeatherLocation) : WeatherLocationEntity {
            return WeatherLocationEntity(
                domain.id,
                domain.name,
                domain.extra,
                domain.country,
                domain.latitude,
                domain.longitude
            )
        }

        fun mapRemoteToDomain(remote : GeocodingSearchItem) : WeatherLocation {

            val cc = remote.country_code.uppercase();

            return WeatherLocation(
                remote.id,
                remote.name,
                remote.admin1.orEmpty(),
                CountryCode.valueOf(cc),
                remote.latitude,
                remote.longitude
            )
        }
    }
}
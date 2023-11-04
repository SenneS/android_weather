package be.senne.meerweer.domain.model

import java.util.UUID

data class WeatherLocation(
    val uuid: UUID,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val elevation : Long
)
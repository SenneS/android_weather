package be.senne.meerweer.domain.model

data class WeatherLocation(
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val elevation : Long
)

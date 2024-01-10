package be.senne.meerweer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.senne.meerweer.domain.model.CountryCode

@Entity("weather_location")
data class WeatherLocationEntity(
    @PrimaryKey
    val id: Long,
    val name : String,
    val extra : String,
    val country : CountryCode,
    val latitude : Double,
    val longitude : Double
)
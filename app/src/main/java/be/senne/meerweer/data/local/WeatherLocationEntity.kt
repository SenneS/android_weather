package be.senne.meerweer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("weather_location")
data class WeatherLocationEntity(
    @PrimaryKey
    val id : Long,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    val elevation : Long
)
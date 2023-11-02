package be.senne.meerweer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("weather_data")
data class WeatherDataEntity(
    @PrimaryKey
    val id : Long,
)

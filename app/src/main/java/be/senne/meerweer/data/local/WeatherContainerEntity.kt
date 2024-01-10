package be.senne.meerweer.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.senne.meerweer.domain.model.WeatherCode
import be.senne.meerweer.domain.model.WeatherWindDirection

@Entity(
    tableName="weather_data",
    foreignKeys = [ForeignKey(
        entity = WeatherLocationEntity::class,
        parentColumns = ["id"],
        childColumns = ["locationId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherContainerEntity(
    @PrimaryKey
    val locationId : Long,
    var timestamp : Long,
    val name : String,
    val temperature : Double,
    val precipitation : Double,
    val weatherCode : WeatherCode,
    val windSpeed : Double,
    val windDirection : WeatherWindDirection,
    val windGusts : Double,
)
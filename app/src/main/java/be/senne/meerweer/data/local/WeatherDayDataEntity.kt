package be.senne.meerweer.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.senne.meerweer.domain.model.WeatherCode
import be.senne.meerweer.domain.model.WeatherWindDirection
import be.senne.meerweer.domain.model.WeekDay

@Entity(
    tableName="weather_day_data",
    foreignKeys = [ForeignKey(
        entity = WeatherContainerEntity::class,
        parentColumns = ["locationId"],
        childColumns = ["locationId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherDayDataEntity(
    @PrimaryKey
    val id : Long? = null,
    @ColumnInfo(index = true)
    val locationId : Long,
    val time : String,
    val sunrise : String,
    val sunset : String,
    val maxPrecipitation : Double,
    val maxWindspeed : Double,
    val maxWindgusts : Double,
    val minTemperature : Double,
    val maxTemperature : Double,
    val weatherCode : WeatherCode,
    val windDirection : WeatherWindDirection
)
package be.senne.meerweer.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.senne.meerweer.domain.model.WeatherCode

@Entity(
    tableName="weather_hour_data",
    foreignKeys = [ForeignKey(
        entity = WeatherContainerEntity::class,
        parentColumns = ["locationId"],
        childColumns = ["locationId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeatherHourDataEntity(
    @PrimaryKey
    val id : Long? = null,
    @ColumnInfo(index = true)
    val locationId : Long,
    val time : String,
    val maxPrecipitation : Double,
    val temperature : Double,
    val weatherCode : WeatherCode
)

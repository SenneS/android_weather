package be.senne.meerweer.data.local

import androidx.room.Embedded
import androidx.room.Relation



data class WeatherDataEntity(
    @Embedded
    val wd : WeatherContainerEntity,

    @Relation(
        entity = WeatherHourDataEntity::class,
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyData : List<WeatherHourDataEntity>,

    @Relation(
        entity = WeatherDayDataEntity::class,
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyData : List<WeatherDayDataEntity>
)
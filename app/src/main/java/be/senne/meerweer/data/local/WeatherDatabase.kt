package be.senne.meerweer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeatherContainerEntity::class,
        WeatherDayDataEntity::class,
        WeatherHourDataEntity::class,
        WeatherLocationEntity::class
               ],
    version = 1,
    exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
}
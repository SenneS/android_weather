package be.senne.meerweer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertWeatherLocations(vararg locations: WeatherLocationEntity)

    @Query("SELECT * FROM weather_location")
    abstract fun getWeatherLocationsFlow() : Flow<List<WeatherLocationEntity>>

    @Query("SELECT * FROM weather_location")
    abstract suspend fun getWeatherLocations() : List<WeatherLocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertWeatherContainers(datas : List<WeatherContainerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertWeatherDailyData(datas : List<WeatherDayDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertWeatherHourlyData(datas : List<WeatherHourDataEntity>)

    @Transaction
    open suspend fun insertWeatherDatas(vararg datas : WeatherDataEntity) {
        datas.forEach {
            it.apply {
                this.wd.timestamp = System.currentTimeMillis();
            }
        }
        insertWeatherContainers(datas.map { x -> x.wd });
        datas.forEach {
            insertWeatherDailyData(it.dailyData);
            insertWeatherHourlyData(it.hourlyData);
        }
    }

    @Query("SELECT * FROM weather_data")
    abstract suspend fun getAllWeatherData() : List<WeatherDataEntity>

}
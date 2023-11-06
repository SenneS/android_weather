package be.senne.meerweer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    protected abstract suspend fun _insertWeatherDatas(vararg datas : WeatherDataEntity)

    suspend fun insertWeatherDatas(vararg datas : WeatherDataEntity) {
        datas.forEach {
            it.apply {
                timestamp = System.currentTimeMillis()
            }
        }
        _insertWeatherDatas(*datas)
    }

    @Query("SELECT * FROM weather_data")
    abstract fun getAllWeatherDataFlow() : Flow<List<WeatherDataEntity>>

    @Query("SELECT * FROM weather_data")
    abstract suspend fun getAllWeatherData() : List<WeatherDataEntity>

    @Query("SELECT * FROM weather_data WHERE id = :id")
    abstract fun getWeatherDataFlow(id : Long) : Flow<WeatherDataEntity>

    @Query("SELECT * FROM weather_data WHERE id = :id")
    abstract suspend fun getWeatherData(id : Long) : WeatherDataEntity
}
package be.senne.meerweer.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.SettingItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private companion object {
        fun <T : Enum<T>> enumSerializer(data : T) : String {
            return data.name
        }
        inline fun <reified T : Enum<T>> enumDeserializer(data : String) : T {
            return enumValueOf<T>(data)
        }

        val serializeMeasurementUnit: (MeasurementUnit) -> String = ::enumSerializer
        val deserializeMeasurementUnit: (String) -> MeasurementUnit = ::enumDeserializer

        val TEMPERATURE_UNIT = SettingItem(stringPreferencesKey("temp_unit"), MeasurementUnit.METRIC, serializeMeasurementUnit, deserializeMeasurementUnit)
        val SPEED_UNIT = SettingItem(stringPreferencesKey("speed_unit"), MeasurementUnit.METRIC, serializeMeasurementUnit, deserializeMeasurementUnit)
        val PRECIPITATION_UNIT = SettingItem(stringPreferencesKey("precipitation_unit"), MeasurementUnit.METRIC, serializeMeasurementUnit, deserializeMeasurementUnit)
    }

    private suspend fun <K, T> getPreference(setting : SettingItem<K, T>) : Result<T> {
        return Result.runCatching {
            val flow = dataStore.data.catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw it
                }
            }.map {
                it[setting.key]
            }

            val value = flow.firstOrNull() ?: return@runCatching setting.default
            setting.deserialize(value)
        }
    }

    private suspend fun <K, T> setPreference(setting: SettingItem<K, T>, value: T) {
        Result.runCatching {
            dataStore.edit {
                it[setting.key] = setting.serialize(value)
            }
        }
    }

    override suspend fun getTemperatureUnit(): Result<MeasurementUnit> {
        return getPreference(TEMPERATURE_UNIT)
    }

    override suspend fun getSpeedUnit(): Result<MeasurementUnit> {
        return getPreference(SPEED_UNIT)
    }

    override suspend fun getPrecipitationUnit(): Result<MeasurementUnit> {
        return getPreference(PRECIPITATION_UNIT)
    }

    override suspend fun setTemperatureUnit(unit: MeasurementUnit) {
        setPreference(TEMPERATURE_UNIT, unit)
    }

    override suspend fun setSpeedUnit(unit: MeasurementUnit) {
        setPreference(SPEED_UNIT, unit)
    }

    override suspend fun setPrecipitationUnit(unit: MeasurementUnit) {
        setPreference(PRECIPITATION_UNIT, unit)
    }

}
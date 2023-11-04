package be.senne.meerweer.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import be.senne.meerweer.domain.model.MeasurementUnit
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private companion object {
        val MEASUREMENT_UNIT = stringPreferencesKey(
            name = "measurement_unit"
        )
    }

    override suspend fun getUnitOfMeasurement(): Result<MeasurementUnit> {
        return Result.runCatching {
            val flow = dataStore.data.catch {
                if(it is IOException) {
                    emit(emptyPreferences())
                }
                else {
                    throw it
                }
            }.map {
                it[MEASUREMENT_UNIT]
            }
            val value = flow.firstOrNull() ?: return@runCatching MeasurementUnit.METRIC
            val unit = MeasurementUnit.valueOf(value)
            unit
        }
    }

    override suspend fun setUnitOfMeasurement(unit: MeasurementUnit) {
        Result.runCatching {
            dataStore.edit {
                it[MEASUREMENT_UNIT] = unit.name
            }
        }
    }

}
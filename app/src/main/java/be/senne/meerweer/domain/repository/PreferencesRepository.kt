package be.senne.meerweer.domain.repository

import be.senne.meerweer.domain.model.MeasurementUnit

interface PreferencesRepository {
    suspend fun getUnitOfMeasurement() : Result<MeasurementUnit>
    suspend fun setUnitOfMeasurement(unit : MeasurementUnit)
}
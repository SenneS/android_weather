package be.senne.meerweer.domain.repository

import be.senne.meerweer.domain.model.MeasurementUnit

interface PreferencesRepository {
    suspend fun getTemperatureUnit() : Result<MeasurementUnit>
    suspend fun getSpeedUnit() : Result<MeasurementUnit>
    suspend fun getPrecipitationUnit() : Result<MeasurementUnit>
    suspend fun setTemperatureUnit(unit : MeasurementUnit)
    suspend fun setSpeedUnit(unit : MeasurementUnit)
    suspend fun setPrecipitationUnit(unit : MeasurementUnit)
}
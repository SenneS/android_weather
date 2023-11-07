package be.senne.meerweer.ui.event

import be.senne.meerweer.domain.model.MeasurementUnit

sealed class SettingsEvent {
    data class SetSpeedUnit(val unit : MeasurementUnit) : SettingsEvent()
    data class SetTemperatureUnit(val unit : MeasurementUnit) : SettingsEvent()
    data class SetPrecipitationUnit(val unit : MeasurementUnit) : SettingsEvent()
}
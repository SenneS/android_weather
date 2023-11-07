package be.senne.meerweer.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.repository.PreferencesRepository
import be.senne.meerweer.ui.event.SettingsEvent
import be.senne.meerweer.ui.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        fetchMeasurementUnits()
    }

    private fun fetchMeasurementUnits() {
        viewModelScope.launch {
            val precipitationUnit = preferencesRepository.getPrecipitationUnit().getOrDefault(MeasurementUnit.METRIC)
            val speedUnit = preferencesRepository.getSpeedUnit().getOrDefault(MeasurementUnit.METRIC)
            val temperatureUnit = preferencesRepository.getTemperatureUnit().getOrDefault(MeasurementUnit.METRIC)

            _state.value = _state.value.copy(
                isLoading = false,
                currentSpeedUnit = speedUnit.ordinal,
                currentTemperatureUnit = temperatureUnit.ordinal,
                currentPrecipitationUnit = precipitationUnit.ordinal
            )

        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.SetPrecipitationUnit -> OnSetPrecipitationUnit(event.unit)
            is SettingsEvent.SetSpeedUnit -> OnSetSpeedUnit(event.unit)
            is SettingsEvent.SetTemperatureUnit -> OnSetTemperatureUnit(event.unit)
        }
    }

    private fun OnSetPrecipitationUnit(unit : MeasurementUnit) {
        viewModelScope.launch {
            preferencesRepository.setPrecipitationUnit(unit)
            _state.value = _state.value.copy(currentPrecipitationUnit = unit.ordinal)
        }
    }

    private fun OnSetSpeedUnit(unit : MeasurementUnit) {
        viewModelScope.launch {
            preferencesRepository.setSpeedUnit(unit)
            _state.value = _state.value.copy(currentSpeedUnit = unit.ordinal)
        }
    }

    private fun OnSetTemperatureUnit(unit : MeasurementUnit) {
        viewModelScope.launch {
            preferencesRepository.setTemperatureUnit(unit)
            _state.value = _state.value.copy(currentTemperatureUnit = unit.ordinal)
        }
    }
}
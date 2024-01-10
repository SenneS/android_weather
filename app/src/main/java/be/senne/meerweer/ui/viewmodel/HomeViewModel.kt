package be.senne.meerweer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.domain.repository.PreferencesRepository
import be.senne.meerweer.domain.repository.WeatherRepository
import be.senne.meerweer.ui.component.fakeWeatherData
import be.senne.meerweer.ui.event.HomeEvent
import be.senne.meerweer.ui.model.WeatherDataUI
import be.senne.meerweer.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            updateWeatherInfo()
        }
    }

    private suspend fun updateWeatherInfo() {
        val cachedData  = weatherRepository.getCachedWeatherData()
        val missingData = weatherRepository.getMissingWeatherData()

        val mapCachedData = mutableMapOf<Int, WeatherData>()
        val mapMissingData = mutableMapOf<Int, WeatherLocation>()

        (cachedData.asSequence() + missingData.asSequence()).withIndex().sortedBy {
            val v = it.value
            when(v) {
                is WeatherData -> { v.name}
                is WeatherLocation -> {v.name}
                else -> { ""}
            }
        }.forEachIndexed { i, it ->
            val v = it.value
            when(v) {
                is WeatherData -> {
                    mapCachedData[i] = v
                }
                is WeatherLocation -> {
                    mapMissingData[i] = v
                }
            }
        }

        val num = cachedData.size + missingData.size

        val speedUnit = preferencesRepository.getSpeedUnit().getOrDefault(MeasurementUnit.METRIC)
        val tempUnit = preferencesRepository.getTemperatureUnit().getOrDefault(MeasurementUnit.METRIC)
        val precipUnit = preferencesRepository.getPrecipitationUnit().getOrDefault(MeasurementUnit.METRIC)

        _state.update { it ->
            it.copy(
                locationsLoading = false,
                locationLoading = false,
                locationCount = num,
                locationData = mapCachedData.mapValues {(i,it) -> WeatherDataUI.fromWeatherData(it, speedUnit, tempUnit, precipUnit) }
            )
        }



        CoroutineScope(Dispatchers.IO).launch {
            mapMissingData.forEach { (i, v) ->
                val data = weatherRepository.updateWeatherData(v, true)
                data.onSuccess {wd ->
                    _state.update {
                        val locationMap = _state.value.locationData.toMutableMap()
                        locationMap[i] = WeatherDataUI.fromWeatherData(wd, speedUnit, tempUnit, precipUnit);
                        it.copy(locationData = locationMap)
                    }
                }
            }
        }
    }

    fun onEvent(event : HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.RefreshAllWeatherData -> {
                    updateWeatherInfo()
                }
            }
        }
    }

}
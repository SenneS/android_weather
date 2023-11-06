package be.senne.meerweer.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.domain.model.WeatherData
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.domain.repository.PreferencesRepository
import be.senne.meerweer.domain.repository.WeatherRepository
import be.senne.meerweer.ui.components.fakeWeatherData
import be.senne.meerweer.ui.model.WeatherDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ViewModel2 @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State2())
    val state: StateFlow<State2> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            updateWeatherInfo()
        }
    }

    private suspend fun updateWeatherInfo() {
        val cachedData  =listOf(
            WeatherData("D10"),
            WeatherData("D20"),
            WeatherData("D30"),
        )//= weatherRepository.getCachedWeatherData()
        val missingData = listOf(
            WeatherLocation(UUID.randomUUID(), "L10", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(), "L20", 0.0, 0.0, 0),
            WeatherLocation(UUID.randomUUID(), "L30", 0.0, 0.0, 0),
        )//= weatherRepository.getMissingWeatherData()

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

        _state.update { it ->
            it.copy(
                locationsLoading = false,
                locationCount = num,
                locationData = mapCachedData.mapValues {(i,it) -> mapWeatherDataToUI(it) }
            )
        }


        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            Log.wtf("", "H2")
            val locationMap = _state.value.locationData.toMutableMap()
            locationMap[4] = fakeWeatherData()
            _state.update { it.copy(locationData = locationMap, locationLoading = false) }
        }

        Log.wtf("", "H1")
    }

    fun onEvent(event : Event2) {
        viewModelScope.launch {
            when (event) {
                is Event2.RefreshAllWeatherData -> {
                    updateWeatherInfo()
                }
            }
        }
    }

    private fun mapWeatherDataToUI(data : WeatherData) : WeatherDataUI {
        return fakeWeatherData()
    }


}
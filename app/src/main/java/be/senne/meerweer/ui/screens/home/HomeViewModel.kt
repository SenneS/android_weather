package be.senne.meerweer.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.data.local.WeatherDao
import be.senne.meerweer.data.local.WeatherLocationEntity
import be.senne.meerweer.data.local.mapper.weatherLocationEntitiesToDomain
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        fetchWeatherLocations()
    }

    private fun fetchWeatherLocations() {
        viewModelScope.launch {
            _state.value = _state.value.copy(areLocationsLoading = true)
            val locations = weatherRepository.getSavedLocations();
            if(locations.isSuccess) {
                _state.value = _state.value.copy(weatherLocations = locations.getOrDefault(ArrayList()), areLocationsLoading = false)
            }
        }
    }

    fun onEvent(event : HomeEvent) {
        when(event) {
            is HomeEvent.RefreshWeatherLocations -> fetchWeatherLocations()
        }
    }
}
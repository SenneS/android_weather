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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
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


    private var weatherLocations : List<WeatherLocation> = emptyList()



    private lateinit var locationState : StateFlow<List<WeatherLocation>>
    private lateinit var weatherData : StateFlow<List<WeatherData>>

    init {
        fetchWeatherLocations()
        //_state.value = _state.value.copy(currentWeatherData = fakeWeatherData())
        viewModelScope.launch {
            //locationState = weatherRepository.getSavedLocations2().stateIn(viewModelScope)
            //weatherData = weatherRepository.getAllWeatherData2().stateIn(viewModelScope)

            locationState.collect() {
                _state.update {
                    it.copy()
                }


                _state.value = _state.value.copy(locationsAreLoading = true)

                _state.value = _state.value.copy(locationsAreLoading = false)
                //handle location updates?
            }

            weatherData.collect() {
                //handle weather updates?
            }
        }
    }



    private fun fetchWeatherLocations() {
        viewModelScope.launch {
            /*
            _state.value = _state.value.copy(locationsAreLoading = true)
            val locations = weatherRepository.getSavedLocations();
            if(locations.isSuccess) {
                weatherLocations = locations.getOrDefault(emptyList())

                val locs = locations.getOrDefault(ArrayList())
                _state.value = _state.value.copy(locationsAreLoading = false, weatherData = List(locs.size){
                    val loc = locs[it]
                    WeatherDataUI(loc.uuid, Instant.EPOCH, loc.name)
                })
                if(locs.isNotEmpty()) {
                    OnRefreshWeatherData(locs.first().uuid)
                }
            }
            */
        }
    }

    fun onEvent(event : HomeEvent) {
        when(event) {
            is HomeEvent.RefreshWeatherLocations -> fetchWeatherLocations()
            is HomeEvent.RefreshWeatherData -> OnRefreshWeatherData(event.uuid)
        }
    }

    private fun OnRefreshWeatherData(uuid : UUID) {
        viewModelScope.launch {
            _state.value = _state.value.copy(dataIsLoading = true)
            Log.wtf("", "OnRefreshWeatherData(uuid: $uuid)")
            delay(1000)

            val newData = fakeWeatherData()
            val list = _state.value.weatherData.toMutableList()

            _state.value = _state.value.copy(dataIsLoading = false, weatherData = list.also {
                for(i in 0 until list.size) {
                    if(it[i].locationUuid == uuid) {
                        it[i] = newData
                    }
                }
            })
        }
    }
}
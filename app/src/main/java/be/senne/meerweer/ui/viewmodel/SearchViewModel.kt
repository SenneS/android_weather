package be.senne.meerweer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.domain.model.MeasurementUnit
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.domain.repository.PreferencesRepository
import be.senne.meerweer.domain.repository.WeatherRepository
import be.senne.meerweer.ui.component.fakeWeatherData
import be.senne.meerweer.ui.event.SearchEvent
import be.senne.meerweer.ui.model.WeatherCurrentDataUI
import be.senne.meerweer.ui.model.WeatherDataUI
import be.senne.meerweer.ui.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private fun OnSearchTermValueChange(term : String) {
        viewModelScope.launch {
            Log.wtf("", "search term: ${term}")
            _state.value = _state.value.copy(searchTerm = term)
        }
    }

    private fun OnSearch(query : String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSearching = true)
            val res = weatherRepository.searchLocations(query).getOrDefault(listOf())
            _state.update {
                it.copy(searchResults=res);
            };
            _state.value = _state.value.copy(isSearching = false)
        }
    }

    private fun OnOpenSearchResult(location: WeatherLocation) {
        viewModelScope.launch {
            Log.wtf("", "OnOpenSearchResult()");

            val speedUnit = preferencesRepository.getSpeedUnit().getOrDefault(MeasurementUnit.METRIC)
            val tempUnit = preferencesRepository.getTemperatureUnit().getOrDefault(MeasurementUnit.METRIC)
            val precipUnit = preferencesRepository.getPrecipitationUnit().getOrDefault(MeasurementUnit.METRIC)

            weatherRepository.updateWeatherData(location, false).onSuccess {
                _state.update { st ->
                    st.copy(isDialog = true, searchResultData = Result.success(WeatherDataUI.fromWeatherData(it, speedUnit, tempUnit, precipUnit)), openLocation = location)
                }
            }.onFailure {
                Log.wtf("", it.message);
                _state.update { st ->
                    st.copy(isDialog = true)
                }
            }
        }
    }

    private fun OnCloseSearchResult() {
        _state.update {
            it.copy(isDialog = false, locationSaved = false)
        }
    }

    private fun OnSaveLocation(location: WeatherLocation) {
        viewModelScope.launch {
            weatherRepository.saveLocation(location)
            _state.update {
                it.copy(locationSaved = true)
            }
            OnCloseSearchResult()
        }
    }

    fun onEvent(event: SearchEvent) {
        viewModelScope.launch {
            when(event) {
                is SearchEvent.SearchTermValueChange -> OnSearchTermValueChange(event.term)
                is SearchEvent.Search -> OnSearch(event.query)
                is SearchEvent.SaveLocation -> OnSaveLocation(event.location)
                is SearchEvent.OpenSearchResult -> OnOpenSearchResult(event.location)
                is SearchEvent.CloseSearchResult -> OnCloseSearchResult()
            }
        }
    }
}
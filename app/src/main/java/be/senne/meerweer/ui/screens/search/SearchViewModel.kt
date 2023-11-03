package be.senne.meerweer.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.senne.meerweer.domain.model.WeatherLocation
import be.senne.meerweer.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val searchDebounce = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            searchDebounce.debounce(1000).collect {
                delay(3000)
                if(it.isEmpty()) {
                    _state.value = _state.value.copy(searchResults = listOf(), isSearching = false)
                }
                else {
                    /*
                    val locations = withContext(Dispatchers.IO) {
                        weatherRepository.searchLocations(it);
                    }
                    */

                    _state.value = _state.value.copy(searchResults = listOf(
                        WeatherLocation("Location 1", 0.0, 0.0, 0),
                        WeatherLocation("Location 2", 0.0, 0.0, 0),
                        WeatherLocation("Location 3", 0.0, 0.0, 0),
                        WeatherLocation("Location 4", 0.0, 0.0, 0),
                    ), isSearching = false)
                }


            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnSearchTermValueChange -> {
                viewModelScope.launch {
                    Log.wtf("", "search term: ${event.term}")
                    searchDebounce.emit(event.term)
                    _state.value = _state.value.copy(searchTerm = event.term, isSearching = true)
                }
            }
        }
    }
}
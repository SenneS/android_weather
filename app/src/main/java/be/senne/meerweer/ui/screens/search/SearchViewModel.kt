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
        /*
        viewModelScope.launch {
            searchDebounce.debounce(1000).collect {
                delay(3000)
                if(it.isNotEmpty()) {
                    val locationsReq = withContext(Dispatchers.IO) {
                        weatherRepository.searchLocations(it);
                    }
                    val locations = locationsReq.getOrDefault(listOf())
                    _state.value = _state.value.copy(searchResults = locations, isSearching = false)
                }
                else {
                    _state.value = _state.value.copy(searchResults = listOf(), isSearching = false)
                }


            }
        }
         */
    }

    private fun OnSearchTermValueChange(term : String) {
        viewModelScope.launch {
            Log.wtf("", "search term: ${term}")
            //searchDebounce.emit(event.term)
            _state.value = _state.value.copy(searchTerm = term)
        }
    }

    private fun OnSearch(query : String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSearching = true)
            delay(1000)
            _state.value = _state.value.copy(isSearching = false)
        }
    }

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.SearchTermValueChange -> OnSearchTermValueChange(event.term)
            is SearchEvent.Search -> OnSearch(event.query)
            is SearchEvent.SaveLocation -> TODO()
        }
    }
}
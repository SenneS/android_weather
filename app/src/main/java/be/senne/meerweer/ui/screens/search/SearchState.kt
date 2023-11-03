package be.senne.meerweer.ui.screens.search

import be.senne.meerweer.domain.model.WeatherLocation

data class SearchState(
    val searchTerm : String = "",
    val isSearching : Boolean = false,
    val searchResults: List<WeatherLocation> = ArrayList()
)
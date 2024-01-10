package be.senne.meerweer.ui.event

import be.senne.meerweer.domain.model.WeatherLocation

sealed class SearchEvent {
    data class SearchTermValueChange(val term : String) : SearchEvent()
    data class Search(val query : String) : SearchEvent()
    data class SaveLocation(val location: WeatherLocation) : SearchEvent()
    data class OpenSearchResult(val location: WeatherLocation) : SearchEvent()
    data class CloseSearchResult(val tmp : String) : SearchEvent()
}
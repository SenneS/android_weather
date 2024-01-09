package be.senne.meerweer.ui.event

sealed class SearchEvent {
    data class SearchTermValueChange(val term : String) : SearchEvent()
    data class Search(val query : String) : SearchEvent()
    data class SaveLocation(val notSureYet: String) : SearchEvent()
    data class OpenSearchResult(val tmp : String) : SearchEvent()
    data class CloseSearchResult(val tmp : String) : SearchEvent()
}
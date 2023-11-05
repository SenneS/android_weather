package be.senne.meerweer.ui.screens.search

sealed class SearchEvent {
    data class SearchTermValueChange(val term : String) : SearchEvent()
    data class Search(val query : String) : SearchEvent()

    data class SaveLocation(val notSureYet: String) : SearchEvent()
}
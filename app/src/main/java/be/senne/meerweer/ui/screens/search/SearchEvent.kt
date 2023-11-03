package be.senne.meerweer.ui.screens.search

sealed class SearchEvent {
    data class OnSearchTermValueChange(val term : String) : SearchEvent()
}
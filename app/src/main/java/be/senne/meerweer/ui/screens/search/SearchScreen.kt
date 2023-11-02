package be.senne.meerweer.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SearchScreen(state : State<SearchState>, onEvent: (SearchEvent) -> Unit) {

}

@Preview
@Composable
fun SearchScreen() {
    val __state = MutableStateFlow(SearchState())
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()

    SearchScreen(state = state, {})
}
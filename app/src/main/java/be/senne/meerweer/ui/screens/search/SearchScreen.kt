package be.senne.meerweer.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen(state : SearchState, onEvent: (SearchEvent) -> Unit) {

}

@Preview
@Composable
fun SearchScreen() {
    val state = SearchState("ABC")
    SearchScreen(state = state, {})
}
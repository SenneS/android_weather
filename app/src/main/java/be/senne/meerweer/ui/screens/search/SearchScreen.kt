package be.senne.meerweer.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.senne.meerweer.ui.event.SearchEvent
import be.senne.meerweer.ui.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SearchScreen(state : State<SearchState>, onEvent: (SearchEvent) -> Unit) {
    val ui = state.value;

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = state.value.searchTerm, onValueChange = {newValue -> onEvent(SearchEvent.SearchTermValueChange(newValue))}, modifier = Modifier.fillMaxWidth(), placeholder = {Text("")})
        Spacer(modifier = Modifier.height(16.dp))

        if(ui.isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(ui.searchResults) {
                    Text("Result: ${it.name}")
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreen() {
    val __state = MutableStateFlow(SearchState())
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()

    SearchScreen(state = state, {})
}
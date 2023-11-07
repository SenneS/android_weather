package be.senne.meerweer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.senne.meerweer.ui.component.SearchResult
import be.senne.meerweer.ui.event.SearchEvent
import be.senne.meerweer.ui.state.SearchState
import be.senne.meerweer.ui.theme.HetWeerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(state : State<SearchState>, onEvent: (SearchEvent) -> Unit) {
    val ui = state.value
    SearchBar(
        query = ui.searchTerm,
        onQueryChange =
        {
            onEvent(SearchEvent.SearchTermValueChange(it))
        },
        onSearch = {
            onEvent(SearchEvent.Search(it))
        },
        active = true,
        onActiveChange = {

        },
        placeholder = {
            Text("Search Locations...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(ui.isSearching) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(count = 20) {
                        SearchResult()
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun SearchScreen2Preview() {
    HetWeerTheme {
        val __state = MutableStateFlow(SearchState())
        val _state = __state.asStateFlow()
        val state = _state.collectAsStateWithLifecycle()
        SearchScreen(state, {})
    }
}
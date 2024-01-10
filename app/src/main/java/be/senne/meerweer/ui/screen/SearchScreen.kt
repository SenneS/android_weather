package be.senne.meerweer.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.senne.meerweer.R
import be.senne.meerweer.ui.component.SearchResult
import be.senne.meerweer.ui.component.WeatherCard
import be.senne.meerweer.ui.component.fakeWeatherData
import be.senne.meerweer.ui.event.SearchEvent
import be.senne.meerweer.ui.model.WeatherDataUI
import be.senne.meerweer.ui.state.SearchState
import be.senne.meerweer.ui.theme.HetWeerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(state : State<SearchState>, onEvent: (SearchEvent) -> Unit) {
    val ui = state.value
    val context = LocalContext.current
    val str = stringResource(R.string.location_saved)

    LaunchedEffect(ui.locationSaved) {
        if(ui.locationSaved) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
    }

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
            Text(stringResource(R.string.searchbar_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.searchbar_description),
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
                    items(items=ui.searchResults, key={it->it.id}) {
                        SearchResult(it) {
                            onEvent(SearchEvent.OpenSearchResult(it))
                        }
                    }
                }
            }
        }

    }

    if(ui.isDialog) {
        ui.searchResultData.getOrNull()?.let{
            CardSelected(
                {
                    onEvent(SearchEvent.SaveLocation(ui.openLocation))
                },
                {
                    onEvent(SearchEvent.CloseSearchResult(""))
                },
                it
            )
        } ?: run {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSelected(
    onSave: () -> Unit,
    onClose: () -> Unit,
    data: WeatherDataUI
) {
    Dialog(
        onDismissRequest =
        {
            onClose()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp, horizontal = 30.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.search_result_title))
                            },
                    actions = {
                        IconButton(onClick = { onSave() }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(
                                R.string.search_result_description
                            )
                            )
                        }
                    }
                )
                WeatherCard(uiData = data)
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
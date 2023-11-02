package be.senne.meerweer.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: State<HomeState>, onEvent: (HomeEvent) -> Unit) {
    val ui = state.value;
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = ui.test)

        Button(onClick = {onEvent(HomeEvent.Button1Clicked("1"))}) {
            Text(ui.test)
        }
        Button(onClick = {onEvent(HomeEvent.Button2Clicked("1"))}) {
            Text(text = "Button 2")
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    val __state = MutableStateFlow(HomeState("ABC"))
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()
    HomeScreen(state = state) {}
}
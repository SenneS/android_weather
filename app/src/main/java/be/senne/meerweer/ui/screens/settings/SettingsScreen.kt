package be.senne.meerweer.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import be.senne.meerweer.ui.event.SettingsEvent
import be.senne.meerweer.ui.state.SettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SettingsScreen(state : State<SettingsState>, onEvent: (SettingsEvent) -> Unit) {

}

@Preview
@Composable
fun SearchScreen() {
    val __state = MutableStateFlow(SettingsState())
    val _state = __state.asStateFlow()
    val state = _state.collectAsStateWithLifecycle()
    SettingsScreen(state = state, {})
}
package be.senne.meerweer.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen(state : SettingsState, onEvent: (SettingsEvent) -> Unit) {

}

@Preview
@Composable
fun SearchScreen() {
    val state = SettingsState("ABC")
    SettingsScreen(state = state, {})
}
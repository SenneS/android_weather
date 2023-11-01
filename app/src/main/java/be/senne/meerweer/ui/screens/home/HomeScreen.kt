package be.senne.meerweer.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = state.test)

        Button(onClick = {onEvent(HomeEvent.Button1Clicked("1"))}) {
            Text("Button 1")
        }
        Button(onClick = {onEvent(HomeEvent.Button2Clicked("1"))}) {
            Text(text = "Button 2")
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    val state = HomeState("ABC")
    HomeScreen(state = state, {})
}
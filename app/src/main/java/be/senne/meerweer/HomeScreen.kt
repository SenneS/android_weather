package be.senne.meerweer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(uiState: HomeUiState = HomeUiState("")) {
    Text(text = uiState.test)
}

@Preview
@Composable
fun HomeScreenPreview() {
    val state = HomeUiState("ABC")
    HomeScreen(uiState = state)
}
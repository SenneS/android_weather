package be.senne.meerweer

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(uiState: HomeUiState = HomeUiState("a"), navController: NavController) {
    Log.wtf("", "text = ${uiState.test}")
    Text(text = uiState.test)
}

@Preview
@Composable
fun HomeScreenPreview() {
    val state = HomeUiState("ABC")
    HomeScreen(uiState = state, rememberNavController())
}
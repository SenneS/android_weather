package be.senne.meerweer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.senne.meerweer.ui.theme.HetWeerTheme

sealed class NavDestination(val route: String) {
    object Home : NavDestination("home")
    object Search : NavDestination("search")
    object Settings : NavDestination("settings")
}
data class NavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun getNavigationItems() : List<NavigationItem> {
        return listOf(
            NavigationItem("Home", icon=Icons.Filled.Home, route = NavDestination.Home.route),
            NavigationItem("Search", icon=Icons.Filled.Search, route = NavDestination.Search.route),
            NavigationItem("Settings", icon=Icons.Filled.Settings, route = NavDestination.Settings.route),
        )
    }
}


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            HetWeerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var selectedIndex by remember {
                        mutableIntStateOf(0)
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {NavigationBar {
                            NavigationItem().getNavigationItems().forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = index == selectedIndex,
                                    onClick = {
                                        Log.wtf("", "index = $index, selectedIndex = $selectedIndex");
                                        selectedIndex = index;
                                        Log.wtf("", "index = $index, selectedIndex = $selectedIndex");
                                        navController.navigate(item.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    })
                            }
                    }}) {
                        NavHost(
                            navController = navController,
                            startDestination = NavDestination.Home.route,
                            modifier = Modifier.padding(paddingValues = it)
                        ) {
                            composable(route = NavDestination.Home.route) {
                                HomeScreen(HomeUiState(it.destination.route.orEmpty()), navController)
                            }
                            composable(route = NavDestination.Search.route) {
                                HomeScreen(HomeUiState(it.destination.route.orEmpty()), navController)
                            }
                            composable(route = NavDestination.Settings.route) {
                                HomeScreen(HomeUiState(it.destination.route.orEmpty()), navController)
                            }
                        }
                    }


                }
            }
        }
    }
}

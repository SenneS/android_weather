package be.senne.meerweer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.senne.meerweer.ui.nav.NavDestination
import be.senne.meerweer.ui.nav.NavItem
import be.senne.meerweer.ui.screens.home.HomeScreen
import be.senne.meerweer.ui.screens.home.HomeViewModel
import be.senne.meerweer.ui.screens.search.SearchScreen
import be.senne.meerweer.ui.screens.search.SearchViewModel
import be.senne.meerweer.ui.screens.settings.SettingsScreen
import be.senne.meerweer.ui.screens.settings.SettingsViewModel
import be.senne.meerweer.ui.theme.HetWeerTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
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
                            NavItem().getNavigationItems().forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = index == selectedIndex,
                                    onClick = {
                                        Log.wtf("", "index = $index, selectedIndex = $selectedIndex");
                                        if(selectedIndex != index) {
                                            selectedIndex = index;
                                            Log.wtf(
                                                "",
                                                "index = $index, selectedIndex = $selectedIndex"
                                            );
                                            navController.navigate(item.route)
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = {
                                        Text(text = item.label)
                                    })
                            }
                    }}) {
                        NavHost(
                            navController = navController,
                            startDestination = NavDestination.Home.route,
                            modifier = Modifier.padding(paddingValues = it)
                        ) {
                            composable(route = NavDestination.Home.route) {
                                val viewModel = hiltViewModel<HomeViewModel>();
                                val state = viewModel.state2.collectAsStateWithLifecycle();
                                HomeScreen(state = state, onEvent = viewModel::onEvent)
                            }
                            composable(route = NavDestination.Search.route) {
                                val viewModel = hiltViewModel<SearchViewModel>();
                                val state = viewModel.state;
                                SearchScreen(state = state, onEvent = viewModel::onEvent)
                            }
                            composable(route = NavDestination.Settings.route) {
                                val viewModel = hiltViewModel<SettingsViewModel>();
                                val state = viewModel.state;
                                SettingsScreen(state = state, onEvent = viewModel::onEvent)
                            }
                        }
                    }


                }
            }
        }
    }
}

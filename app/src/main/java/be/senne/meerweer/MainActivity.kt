package be.senne.meerweer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import be.senne.meerweer.ui.component.WeatherApp
import be.senne.meerweer.ui.theme.HetWeerTheme
import be.senne.meerweer.utils.DevicePosture
import be.senne.meerweer.utils.isBookPosture
import be.senne.meerweer.utils.isSeparating
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val devicePostureFlow =  WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )



        setContent {
            HetWeerTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                val devicePosture = devicePostureFlow.collectAsState().value

                WeatherApp(windowSizeClass, devicePosture)

                /*

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
                            NavItem.getNavigationItems().forEachIndexed { index, item ->
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
                                val state = viewModel.state.collectAsStateWithLifecycle();
                                HomeScreen(state = state, onEvent = viewModel::onEvent)
                            }
                            composable(route = NavDestination.Search.route) {
                                val viewModel = hiltViewModel<SearchViewModel>();
                                val state = viewModel.state.collectAsStateWithLifecycle();
                                SearchScreen(state = state, onEvent = viewModel::onEvent)
                            }
                            composable(route = NavDestination.Settings.route) {
                                val viewModel = hiltViewModel<SettingsViewModel>();
                                val state = viewModel.state.collectAsStateWithLifecycle();
                                SettingsScreen(state = state, onEvent = viewModel::onEvent)
                            }
                        }
                    }


                }*/
            }
        }
    }
}

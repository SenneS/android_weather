package be.senne.meerweer.ui.components2

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.senne.meerweer.ui.nav.NavDestination
import be.senne.meerweer.ui.nav.NavigationAction
import be.senne.meerweer.ui.nav.NavigationDestination
import be.senne.meerweer.ui.screens.settings.SettingsViewModel
import be.senne.meerweer.ui.theme.HetWeerTheme
import be.senne.meerweer.utils.DevicePosture
import kotlinx.coroutines.launch

enum class NavigationType {
    NAV_BAR,
    NAV_RAIL,
    NAV_DRAWER
}
enum class NavigationContent {
    LIST,
    DETAILED
}
enum class NavigationAlignment {
    TOP,
    CENTER
}


@Composable
fun WeatherApp(
    windowSizeClass : WindowSizeClass,
    devicePosture: DevicePosture
) {

    val navType: NavigationType
    val navContent: NavigationContent

    when(windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navType = NavigationType.NAV_BAR
            navContent = NavigationContent.LIST
        }
        WindowWidthSizeClass.Medium -> {
            navType = NavigationType.NAV_RAIL
            if(devicePosture is DevicePosture.NormalPosture) {
                navContent = NavigationContent.LIST
            } else {
                navContent = NavigationContent.DETAILED
            }
        }
        WindowWidthSizeClass.Expanded -> {
            if(devicePosture is DevicePosture.BookPosture) {
                navType = NavigationType.NAV_RAIL
            } else {
                navType = NavigationType.NAV_DRAWER
            }
            navContent = NavigationContent.DETAILED
        }
        else -> {
            navType = NavigationType.NAV_BAR
            navContent = NavigationContent.LIST
        }
    }

    val navAlignment = when(windowSizeClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> NavigationAlignment.TOP
        WindowHeightSizeClass.Medium -> NavigationAlignment.CENTER
        WindowHeightSizeClass.Expanded -> NavigationAlignment.CENTER
        else -> NavigationAlignment.TOP
    }

    val navController = rememberNavController()
    val navAction = remember(navController) {
        NavigationAction(navController)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //drawerState .open() .close() are suspend functions so you can't just invoke them from within a composable
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: NavDestination.Home.route

    if(navType == NavigationType.NAV_BAR) {
        WeatherAppContent(navType, navContent, navAlignment, navController, navAction::navigateTo, selectedDestination)
    }
    else if(navType == NavigationType.NAV_RAIL) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    WeatherAppNavigationDrawerContent(selectedDestination, navType, navAction::navigateTo) {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
                            },
            drawerState = drawerState
        ) {
            WeatherAppContent(navType, navContent, navAlignment, navController, navAction::navigateTo, selectedDestination, {
                scope.launch {
                    drawerState.open()
                }
            })
        }
    }
    else if(navType == NavigationType.NAV_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet {
                WeatherAppNavigationDrawerContent(selectedDestination, navType, navAction::navigateTo)
            }
        }) {
            WeatherAppContent(navType, navContent, navAlignment, navController, navAction::navigateTo, selectedDestination)
        }
    }

}

@Composable
fun WeatherAppContent(
    navigationType: NavigationType,
    navContent: NavigationContent,
    navAlignment: NavigationAlignment,
    navController: NavHostController,
    navAction: (NavigationDestination) -> Unit,
    selectedDestination: String,
    onNavDrawerClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAV_RAIL) {
            WeatherAppNavigationRail(selectedDestination, navAlignment, navAction, onNavDrawerClick)
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)) {

            WeatherAppNavHost(navController, Modifier.weight(1f))
            AnimatedVisibility(visible = navigationType == NavigationType.NAV_BAR) {
                WeatherAppBottomNavigation(selectedDestination, navAction)
            }
        }
    }
}

@Composable
fun WeatherAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = modifier
    ) {
        composable(NavDestination.Home.route) {
            Text("HOME")
        }
        composable(NavDestination.Search.route) {

        }
        composable(NavDestination.Settings.route) {

            val viewModel = hiltViewModel<SettingsViewModel>()
            val state = viewModel.state.collectAsStateWithLifecycle()
            SettingsScreen2(state = state, onEvent = viewModel::onEvent)
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun WeatherAppPreview() {
    val windowSizeClass : WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp))
    val devicePosture : DevicePosture = DevicePosture.NormalPosture

    HetWeerTheme {
        WeatherApp(windowSizeClass = windowSizeClass, devicePosture = devicePosture)
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun WeatherAppPreviewTablet() {
    val windowSizeClass : WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp))
    val devicePosture : DevicePosture = DevicePosture.NormalPosture

    HetWeerTheme {
        WeatherApp(windowSizeClass = windowSizeClass, devicePosture = devicePosture)
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun WeatherAppPreviewTabletPortrait() {
    val windowSizeClass : WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp))
    val devicePosture : DevicePosture = DevicePosture.NormalPosture

    HetWeerTheme {
        WeatherApp(windowSizeClass = windowSizeClass, devicePosture = devicePosture)
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun WeatherAppPreviewDesktop() {
    val windowSizeClass : WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp))
    val devicePosture : DevicePosture = DevicePosture.NormalPosture
    
    HetWeerTheme {
        WeatherApp(windowSizeClass = windowSizeClass, devicePosture = devicePosture)
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun WeatherAppPreviewDesktopPortrait() {
    val windowSizeClass : WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp))
    val devicePosture : DevicePosture = DevicePosture.NormalPosture

    HetWeerTheme {
        WeatherApp(windowSizeClass = windowSizeClass, devicePosture = devicePosture)
    }
}
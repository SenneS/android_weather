package be.senne.meerweer.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationDestination(
    val route : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val label : String
) {
    companion object {
        fun Items() : List<NavigationDestination> {
            return listOf(
                NavigationDestination(
                    NavigationRoute.Home.route,
                    Icons.Default.Home,
                    Icons.Default.Home,
                    "Home"
                ),
                NavigationDestination(
                    NavigationRoute.Search.route,
                    Icons.Default.Search,
                    Icons.Default.Search,
                    "Search"
                ),
                NavigationDestination(
                    NavigationRoute.Settings.route,
                    Icons.Default.Settings,
                    Icons.Default.Settings,
                    "Settings"
                ),
            )
        }
    }
}

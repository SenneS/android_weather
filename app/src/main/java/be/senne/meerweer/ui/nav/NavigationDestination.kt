package be.senne.meerweer.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import be.senne.meerweer.R

data class NavigationDestination(
    val route : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val label : Int,
    val contentDescription : Int
) {
    companion object {
        fun Items() : List<NavigationDestination> {
            return listOf(
                NavigationDestination(
                    NavigationRoute.Home.route,
                    Icons.Default.Home,
                    Icons.Default.Home,
                    R.string.nav_home_label,
                    R.string.nav_home_description
                ),
                NavigationDestination(
                    NavigationRoute.Search.route,
                    Icons.Default.Search,
                    Icons.Default.Search,
                    R.string.nav_search_label,
                    R.string.nav_search_description
                ),
                NavigationDestination(
                    NavigationRoute.Settings.route,
                    Icons.Default.Settings,
                    Icons.Default.Settings,
                    R.string.nav_settings_label,
                    R.string.nav_settings_description
                ),
            )
        }
    }
}

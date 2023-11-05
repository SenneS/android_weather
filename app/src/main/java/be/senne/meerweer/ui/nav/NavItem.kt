package be.senne.meerweer.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {

    companion object {
        fun getNavigationItems(): List<NavItem> {
            return listOf(
                NavItem("Home", icon = Icons.Filled.Home, route = NavDestination.Home.route),
                NavItem("Search", icon = Icons.Filled.Search, route = NavDestination.Search.route),
                NavItem(
                    "Settings",
                    icon = Icons.Filled.Settings,
                    route = NavDestination.Settings.route
                ),
            )
        }
    }
}
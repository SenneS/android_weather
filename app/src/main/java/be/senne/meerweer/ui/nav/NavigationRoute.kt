package be.senne.meerweer.ui.nav

sealed class NavigationRoute(val route : String) {
    object Home : NavigationRoute("home")
    object Search : NavigationRoute("search")
    object Settings : NavigationRoute("settings")
}

package be.senne.meerweer.ui.nav

sealed class NavDestination(val route: String) {
    object Home : NavDestination("home")
    object Search : NavDestination("search")
    object Settings : NavDestination("settings")

    data class  SearchResult(val id : String) : NavDestination("search/$id")
}
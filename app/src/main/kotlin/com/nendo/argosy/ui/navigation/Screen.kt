package com.nendo.argosy.ui.navigation

sealed class Screen(val route: String) {
    data object FirstRun : Screen("first_run")
    data object Home : Screen("home")
    data object Library : Screen("library?platformId={platformId}") {
        fun createRoute(platformId: String? = null): String =
            if (platformId != null) "library?platformId=$platformId" else "library"
    }
    data object Downloads : Screen("downloads")
    data object Apps : Screen("apps")
    data object Settings : Screen("settings")
    data object GameDetail : Screen("game/{gameId}") {
        fun createRoute(gameId: Long) = "game/$gameId"
    }
    data object Search : Screen("search")

    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_LIBRARY = "library"
        const val ROUTE_GAME_DETAIL = "game"
        const val ROUTE_SETTINGS = "settings"
        const val ROUTE_DOWNLOADS = "downloads"
        const val ROUTE_APPS = "apps"
        const val ROUTE_SEARCH = "search"
    }
}

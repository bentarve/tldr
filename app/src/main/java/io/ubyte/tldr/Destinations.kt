package io.ubyte.tldr

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "home"
}

object Search : Destinations {
    override val route = "search"
}

object Pages : Destinations {
    override val route = "pages"
    const val pageArg = "pageId"
    val routeWithArgs = "$route/{$pageArg}"
    val arguments = listOf(
        navArgument(pageArg) { type = NavType.LongType }
    )
}

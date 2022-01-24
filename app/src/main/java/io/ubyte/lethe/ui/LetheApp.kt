package io.ubyte.lethe.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ubyte.lethe.ui.LetheDestinations.HOME_ROUTE
import io.ubyte.lethe.ui.LetheDestinations.PAGE_DETAIL_ROUTE
import io.ubyte.lethe.ui.LetheDestinations.PAGE_NAME
import io.ubyte.lethe.ui.LetheDestinations.SEARCH_ROUTE
import io.ubyte.lethe.ui.home.Home
import io.ubyte.lethe.ui.theme.LetheTheme

@Composable
fun LetheApp() {
    LetheTheme {
        val navController: NavHostController = rememberNavController()
        Scaffold { innerPadding ->
            LetheNavHost(
                navController,
                Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun LetheNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier
    ) {
        composable(HOME_ROUTE) {
            Home(
                onSearchClick = { },
                onPageClick = { }
//                onSearchClick = { navController.navigate(SEARCH_ROUTE)},
//                onPageClick = { name -> navigateToPageDetail(navController, name) }
            )
        }
        composable(SEARCH_ROUTE) {
            // todo Search()
        }
        composable(
            route = "${PAGE_DETAIL_ROUTE}/${PAGE_NAME}",
            arguments = listOf(navArgument(PAGE_NAME) { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val pageName = arguments.getString(PAGE_NAME)
            // todo PageDetail(pageName, upPress)
        }
    }
}

private fun navigateToPageDetail(navController: NavController, pageName: String) {
    navController.navigate("${PAGE_DETAIL_ROUTE}/$pageName")
}

object LetheDestinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val PAGE_DETAIL_ROUTE = "pageDetail"
    const val PAGE_NAME = "name"
}

package io.ubyte.tldr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.ubyte.tldr.Destinations.HOME_ROUTE
import io.ubyte.tldr.Destinations.PAGES_ROUTE
import io.ubyte.tldr.Destinations.PAGE_ID
import io.ubyte.tldr.Destinations.SEARCH_ROUTE
import io.ubyte.tldr.home.Home
import io.ubyte.tldr.pagedetails.PageDetails
import io.ubyte.tldr.search.Search
import io.ubyte.tldr.theme.TldrTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TldrTheme {
                val navController: NavHostController = rememberNavController()
                Surface(Modifier.fillMaxSize()) {
                    TldrNavHost(navController)
                }
            }
        }
    }
}

@Composable
private fun TldrNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        composable(HOME_ROUTE) {
            Home(
                viewModel = hiltViewModel(),
                openSearch = { navController.navigate(SEARCH_ROUTE) },
                openPageDetails = { pageId -> navigateToPageDetails(navController, pageId) }
            )
        }
        composable(SEARCH_ROUTE) {
            Search(
                viewModel = hiltViewModel(),
                openPageDetails = { pageId -> navigateToPageDetails(navController, pageId) }
            )
        }
        composable(
            route = PAGES_ROUTE with PAGE_ID,
            arguments = listOf(navArgument(PAGE_ID) { type = NavType.LongType }),
        ) {
            PageDetails(
                viewModel = hiltViewModel(),
                navigateUp = navController::navigateUp
            )
        }
    }
}

private fun navigateToPageDetails(navController: NavController, pageId: Long) {
    navController.navigate(PAGES_ROUTE with pageId)
}

// The route must be specified with { }, navigating to the destination does not
private infix fun String.with(argument: String) = "$this/{$argument}"
private infix fun String.with(argument: Long) = "$this/$argument"

object Destinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val PAGES_ROUTE = "pages"
    const val PAGE_ID = "pageId"
}

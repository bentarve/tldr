package io.ubyte.lethe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.ubyte.lethe.Destinations.HOME_ROUTE
import io.ubyte.lethe.Destinations.PAGES_ROUTE
import io.ubyte.lethe.Destinations.PAGE_ID
import io.ubyte.lethe.Destinations.SEARCH_ROUTE
import io.ubyte.lethe.core.ui.theme.LetheTheme
import io.ubyte.lethe.home.Home
import io.ubyte.lethe.pagedetails.PageDetails
import io.ubyte.lethe.pages.Pages
import io.ubyte.lethe.search.Search

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetheTheme {
                val navController: NavHostController = rememberNavController()
                Surface {
                    LetheNavHost(navController)
                }
            }
        }
    }
}

@Composable
private fun LetheNavHost(navController: NavHostController) {
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
                openPageDetails = { pageId -> navigateToPageDetails(navController, pageId) },
                navigateUp = navController::navigateUp
            )
        }
        composable(PAGES_ROUTE) {
            Pages(
                viewModel = hiltViewModel(),
                openSearch = { navController.navigate(SEARCH_ROUTE) },
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
    navController.navigate("$PAGES_ROUTE/$pageId")
}

private infix fun String.with(argument: String) = "$this/{$argument}"

object Destinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val PAGES_ROUTE = "pages"
    const val PAGE_ID = "pageId"
}

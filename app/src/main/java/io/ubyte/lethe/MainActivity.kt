package io.ubyte.lethe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.ubyte.lethe.Destinations.HOME_ROUTE
import io.ubyte.lethe.Destinations.PAGE_DETAILS_ROUTE
import io.ubyte.lethe.Destinations.PAGE_ID
import io.ubyte.lethe.Destinations.SEARCH_ROUTE
import io.ubyte.lethe.core.ui.theme.LetheTheme
import io.ubyte.lethe.home.Home
import io.ubyte.lethe.pagedetails.PageDetails

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
                hiltViewModel(),
                openSearch = { /* navController.navigate(SEARCH_ROUTE) */ },
                openPageDetails = { pageId -> navigateToPageDetail(navController, pageId) }
            )
        }
        composable(SEARCH_ROUTE) {
            // todo Search()
        }
        composable(
            route = PAGE_DETAILS_ROUTE with PAGE_ID,
            arguments = listOf(navArgument(PAGE_ID) { type = NavType.LongType }),
        ) {
            PageDetails(
                hiltViewModel(),
                navController::navigateUp
            )
        }
    }
}

private fun navigateToPageDetail(navController: NavController, pageId: Long) {
    navController.navigate("$PAGE_DETAILS_ROUTE/$pageId")
}

private infix fun String.with(argument: String) = "$this/{$argument}"

object Destinations {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val PAGE_DETAILS_ROUTE = "pageDetails"
    const val PAGE_ID = "pageId"
}

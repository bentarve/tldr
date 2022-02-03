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
import io.ubyte.lethe.LetheDestinations.HOME_ROUTE
import io.ubyte.lethe.LetheDestinations.PAGE_DETAIL_ROUTE
import io.ubyte.lethe.LetheDestinations.PAGE_ID
import io.ubyte.lethe.LetheDestinations.SEARCH_ROUTE
import io.ubyte.lethe.core.ui.theme.LetheTheme
import io.ubyte.lethe.home.Home

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
                openPageDetails = { /* name -> navigateToPageDetail(navController, name) */ }
            )
        }
        composable(SEARCH_ROUTE) {
            // todo Search()
        }
        composable(
            route = "${PAGE_DETAIL_ROUTE}/${PAGE_ID}",
            arguments = listOf(navArgument(PAGE_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val pageName = arguments.getLong(PAGE_ID)
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
    const val PAGE_ID = "pageId"
}

package io.ubyte.tldr

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.ubyte.tldr.home.Home
import io.ubyte.tldr.pagedetails.PageDetails
import io.ubyte.tldr.search.Search

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            Home(
                viewModel = hiltViewModel(),
                openSearch = { navController.navigate(Search.route) },
                openPageDetails = { pageId -> navController.navigate(pageId) }
            )
        }
        composable(Search.route) {
            Search(
                viewModel = hiltViewModel(),
                openPageDetails = { pageId -> navController.navigate(pageId) }
            )
        }
        composable(
            route = Pages.routeWithArgs,
            arguments = Pages.arguments,
        ) {
            PageDetails(
                viewModel = hiltViewModel(),
                navigateUp = navController::navigateUp
            )
        }
    }
}

private fun NavHostController.navigate(pageId: Long) {
    this.navigate("${Pages.route}/$pageId")
}

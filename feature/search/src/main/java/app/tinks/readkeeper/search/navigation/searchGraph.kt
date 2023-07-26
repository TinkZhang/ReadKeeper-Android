package app.tinks.readkeeper.search.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.search.SearchPage
import app.tinks.readkeeper.search.SearchResultPage

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.searchGraph(navController: NavController) {
    composable(SCREEN_ROUTE.SEARCH) {
        SearchPage(
            onSearch = { navController.navigate("search_result/${it}") },
            onHistoryItemClick = { navController.navigate("search_result/${it}") },
            onBackClick = { navController.popBackStack() }
        )
    }
    composable(
        route = SCREEN_ROUTE.SEARCH_RESUTL,
        arguments = listOf(navArgument("keyword") {
            type = NavType.StringType
        })
    ) {
        SearchResultPage(
            onTitleClick = { navController.popBackStack() },
            onBackClick = {
                navController.popBackStack(
                    SCREEN_ROUTE.HOME,
                    false
                )
            }
        )
    }
}
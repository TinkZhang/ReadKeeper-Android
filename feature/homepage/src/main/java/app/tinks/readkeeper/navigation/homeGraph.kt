package app.tinks.readkeeper.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.homepage.Homepage
import app.tinks.readkeeper.homepage.weeklybook.ui.WeeklyBookVIP

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = SCREEN_ROUTE.HOME,
        route = "Home Flow"
    ) {
        composable(SCREEN_ROUTE.HOME) {
            Homepage(
                navController = navController
            )
        }
        composable(
            route = SCREEN_ROUTE.WEEKLY_ITEM,
            arguments = listOf(navArgument(name = "title") {
                type = NavType.StringType
            })
        ) {
            WeeklyBookVIP(
                navController = navController
            )
        }
    }
}
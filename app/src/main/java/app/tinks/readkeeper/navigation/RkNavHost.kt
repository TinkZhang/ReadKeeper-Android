@file:OptIn(ExperimentalMaterial3Api::class)

package app.tinks.readkeeper.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.search.navigation.searchGraph
import app.tinks.readkeeper.settings.SettingsPage
import app.tinks.readkeeper.uicomponent.detail.BookDetailPage
import app.tinks.readkeeper.uicomponent.editbook.BookEditPage
import app.tinks.readkeeper.uicomponent.list.BookListPage
import app.tinks.readkeeper.uicomponent.notelist.NoteListPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RkNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "Home Flow") {
        homeGraph(navController)
        searchGraph(navController)
        composable(SCREEN_ROUTE.SETTINGS) {
            SettingsPage(
                navController = navController
            )
        }
        composable(SCREEN_ROUTE.WISH_LIST) {
            BookListPage(
                status = Status.WISH,
                navController = navController
            )
        }
        composable(
            SCREEN_ROUTE.WISH_ITEM,
            arguments = listOf(navArgument("uuid") {
                type = NavType.StringType
            })
        ) {
            BookDetailPage(
                uuid = it.arguments?.getString("uuid") ?: "",
                navController = navController
            )
        }
        composable(SCREEN_ROUTE.READING_LIST) {
            BookListPage(
                status = Status.READING,
                navController = navController
            )
        }
        composable(
            SCREEN_ROUTE.READING_ITEM,
            arguments = listOf(navArgument("uuid") {
                type = NavType.StringType
            }, navArgument("open_progress_dialog") {
                type = NavType.BoolType
                defaultValue = false
            }, navArgument("open_edit_dialog") {
                type = NavType.BoolType
                defaultValue = false
            })
        ) {
            BookDetailPage(
                uuid = it.arguments?.getString("uuid") ?: "",
                openAddProgressDialog = it.arguments?.getBoolean("open_progress_dialog") ?: false,
                navController = navController
            )
        }
        composable(
            SCREEN_ROUTE.ALL_NOTES,
            arguments = listOf(navArgument("uuid") {
                type = NavType.StringType
            })
        ) {
            NoteListPage(
                uuid = it.arguments?.getString("uuid"),
                navController = navController
            )
        }
        composable(SCREEN_ROUTE.ARCHIVED_LIST) {
            BookListPage(
                status = Status.ARCHIVED,
                navController = navController
            )
        }
        composable(
            SCREEN_ROUTE.EDIT_BOOK,
            arguments = listOf(navArgument("uuid") {
                type = NavType.StringType
            })
        ) {
            BookEditPage(
                uuid = it.arguments?.getString("uuid"),
                navController = navController
            )
        }
        composable(
            SCREEN_ROUTE.ARCHIVED_ITEM,
            arguments = listOf(navArgument("uuid") {
                type = NavType.StringType
            })
        ) {
            BookDetailPage(
                uuid = it.arguments?.getString("uuid") ?: "",
                navController = navController
            )
        }
    }
}


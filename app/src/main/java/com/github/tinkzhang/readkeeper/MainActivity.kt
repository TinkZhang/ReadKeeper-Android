package com.github.tinkzhang.readkeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.tinkzhang.readkeeper.common.RkScreen
import com.github.tinkzhang.readkeeper.reading.ReadingItemScreen
import com.github.tinkzhang.readkeeper.reading.ReadingListScreen
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.search.SearchScreen
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.github.tinkzhang.readkeeper.ui.MainScreenViewData
import com.github.tinkzhang.readkeeper.ui.ROUTE_TO_SCREEN_MAP
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE
import com.github.tinkzhang.readkeeper.ui.components.RkMainTopBar
import com.github.tinkzhang.readkeeper.ui.getBottomBarItemList
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme
import com.github.tinkzhang.readkeeper.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userViewModel: UserViewModel = viewModel()
            val readingViewModel: ReadingViewModel = viewModel()
            ReadKeeperTheme {
                val currentScreen by rememberSaveable { mutableStateOf(RkScreen.Home) }
                val navController = rememberNavController()
                val context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface {
                    val route =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    val screen = ROUTE_TO_SCREEN_MAP[route]
                    Scaffold(
                        topBar = {
                            if (screen is MainScreenViewData) {
                                RkMainTopBar(
                                    userViewModel = userViewModel,
                                    onProfileClickAction = {
                                        context.startActivity(
                                            Intent(context, SettingsActivity::class.java)
                                        )
                                    })
                            }
                        },
                        bottomBar = {
                            if (screen is MainScreenViewData) {
                                RkNavigationBar(navController = navController)
                            }
                        },
                    ) {
                        NavHost(navController, startDestination = SCREEN_ROUTE.HOME) {
                            composable(SCREEN_ROUTE.HOME) {
                                HomeScreen(
                                    navController = navController,
                                    readingViewModel
                                )
                            }
                            composable(SCREEN_ROUTE.SEARCH) {
                                SearchScreen(navController = navController)
                            }
                            composable(SCREEN_ROUTE.SEARCH_RESUTL) {
                                SearchResultScreen(it.arguments?.getString("keyword") ?: "")
                            }
                            composable(SCREEN_ROUTE.WISH_LIST) {
                                WishListScreen()
                            }
                            composable(SCREEN_ROUTE.READING_LIST) {
                                ReadingListScreen(readingViewModel, navController = navController)
                            }
                            composable(SCREEN_ROUTE.READING_ITEM) {
                                ReadingItemScreen(
                                    it.arguments?.getString("uuid") ?: "",
                                    readingViewModel,
                                    navController = navController
                                )
                            }
                            composable(SCREEN_ROUTE.ARCHIVED_LIST) {
                                WishListScreen()
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun RkNavigationBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        getBottomBarItemList().forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                        Icon(
                            screen.selectedIcon,
                            contentDescription = stringResource(id = screen.labelId)
                        )
                    } else {
                        Icon(
                            screen.unselectedIcon,
                            contentDescription = stringResource(id = screen.labelId)
                        )
                    }
                },
                label = { Text(stringResource(id = screen.labelId)) },
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}


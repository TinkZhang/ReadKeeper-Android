package com.github.tinkzhang.readkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.github.tinkzhang.readkeeper.search.SearchViewModel
import com.github.tinkzhang.readkeeper.ui.ReadingListPage
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE
import com.github.tinkzhang.readkeeper.ui.getBottomBarItemList
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val searchViewModel: SearchViewModel = viewModel()
            ReadKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination
                                getBottomBarItemList().forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentDestination?.hierarchy?.any{ it.route == screen.route} == true,
                                        icon = {
                                            Icon(
                                                screen.icon,
                                                contentDescription = stringResource(id = screen.labelId),
                                            )
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
                    ) {
                        NavHost(navController, startDestination = SCREEN_ROUTE.HOME) {
                            composable(SCREEN_ROUTE.HOME) {
                                HomePage(navController = navController, searchViewModel)
                            }
                            composable(SCREEN_ROUTE.SEARCH) {
                                SearchResultPage(searchViewModel)
                            }
                            composable(SCREEN_ROUTE.WISH_LIST) {
                                WishListPage()
                            }
                            composable(SCREEN_ROUTE.READING_LIST) {
                                ReadingListPage()
                            }
                            composable(SCREEN_ROUTE.ARCHIVED_LIST) {
                                WishListPage()
                            }
                        }
                    }
                }
            }
        }
    }
}

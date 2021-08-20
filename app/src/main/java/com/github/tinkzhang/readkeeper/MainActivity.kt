package com.github.tinkzhang.readkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.github.tinkzhang.readkeeper.instabug.InstabugWrapper
import com.github.tinkzhang.readkeeper.search.SearchViewModel
import com.github.tinkzhang.readkeeper.ui.ReadingListScreen
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE
import com.github.tinkzhang.readkeeper.ui.components.RkTopBar
import com.github.tinkzhang.readkeeper.ui.getBottomBarItemList
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val searchViewModel: SearchViewModel = viewModel()
            ReadKeeperTheme {
                val currentScreen by rememberSaveable { mutableStateOf(RkScreen.Home) }
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = { RkTopBar(onProfileClickAction = { InstabugWrapper.show() }) },
                        bottomBar = { BottomBar(navController = navController) },
                    ) {
                        NavHost(navController, startDestination = SCREEN_ROUTE.HOME) {
                            composable(SCREEN_ROUTE.HOME) {
                                HomeScreen(navController = navController, searchViewModel)
                            }
                            composable(SCREEN_ROUTE.SEARCH) {
                                SearchResultScreen(searchViewModel)
                            }
                            composable(SCREEN_ROUTE.WISH_LIST) {
                                WishListScreen()
                            }
                            composable(SCREEN_ROUTE.READING_LIST) {
                                ReadingListScreen()
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
fun BottomBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        getBottomBarItemList().forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        screen.icon, contentDescription = stringResource(id = screen.labelId),
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


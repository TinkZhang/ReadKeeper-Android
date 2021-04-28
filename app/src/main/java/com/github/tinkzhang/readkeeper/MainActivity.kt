package com.github.tinkzhang.readkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import com.github.tinkzhang.readkeeper.ui.ReadingListPage
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE
import com.github.tinkzhang.readkeeper.ui.getBottomBarItemList
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ReadKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute =
                                    navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                                getBottomBarItemList().forEach { screen ->
                                    BottomNavigationItem(
                                        selected = currentRoute == screen.route,
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
                                                popUpTo = navController.graph.startDestination
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                            }
                                        },
                                    )
                                }
                            }
                        }
                    ) {
                        NavHost(navController, startDestination = SCREEN_ROUTE.HOME) {
                            composable(SCREEN_ROUTE.HOME) {
                                HomePage(navController = navController)
                            }
                            composable(SCREEN_ROUTE.SEARCH) {
                                SearchResultPage(keyword = it.arguments?.getString("keyword"))
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

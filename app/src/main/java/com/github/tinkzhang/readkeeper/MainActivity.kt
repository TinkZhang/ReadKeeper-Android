package com.github.tinkzhang.readkeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.tinkzhang.readkeeper.common.RkScreen
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.search.SearchScreen
import com.github.tinkzhang.readkeeper.settings.SettingsActivity
import com.github.tinkzhang.readkeeper.ui.*
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.SEARCH
import com.github.tinkzhang.readkeeper.ui.components.RkMainTopBar
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
                            } else if (screen is SubScreenViewData) {
                                SmallTopAppBar(title = {
                                    when (screen.route) {
                                        SEARCH -> {
                                            RkSearchTextField(
                                                onSearch = { keyword ->
                                                    navController.navigate("search_result/${keyword}")
                                                }
                                            )
                                        }
                                        else -> {
                                            Text(screen.title)
                                        }
                                    }
                                },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                                        }
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
                                SearchScreen()
                            }
                            composable(SCREEN_ROUTE.SEARCH_RESUTL) {
                                SearchResultScreen(it.arguments?.getString("keyword") ?: "")
                            }
                            composable(SCREEN_ROUTE.WISH_LIST) {
                                WishListScreen()
                            }
                            composable(SCREEN_ROUTE.READING_LIST) {
                                ReadingListScreen(readingViewModel)
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

    @Composable
    private fun RkSearchTextField(
        onSearch: (String) -> Unit,
    ) {
        var keyword by remember { mutableStateOf("") }

        val focusRequester = FocusRequester()
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
        // TODO: replace to M3 when it's available
        OutlinedTextField(
            value = keyword,
            onValueChange = {
                keyword = it
            },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(keyword)
                }
            ),
            trailingIcon = {
                IconButton(onClick = { onSearch(keyword) }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
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


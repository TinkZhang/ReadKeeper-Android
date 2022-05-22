package com.github.tinkzhang.readkeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.readkeeper.archived.ArchivedViewModel
import com.github.readkeeper.archived.ui.ArchivedListPage
import com.github.readkeeper.archived.ui.ArchivedVip
import com.github.tinkzhang.basic.LoginStatus
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import com.github.tinkzhang.homepage.Homepage
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import com.github.tinkzhang.homepage.weeklybook.ui.WeeklyBookVIP
import com.github.tinkzhang.reading.ReadingViewModel
import com.github.tinkzhang.reading.ui.ReadingListPage
import com.github.tinkzhang.readkeeper.reading.ReadingVip
import com.github.tinkzhang.readkeeper.ui.MainScreenViewData
import com.github.tinkzhang.readkeeper.ui.ROUTE_TO_SCREEN_MAP
import com.github.tinkzhang.readkeeper.ui.components.RkMainTopBar
import com.github.tinkzhang.readkeeper.ui.getBottomBarItemList
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme
import com.github.tinkzhang.search.SearchPage
import com.github.tinkzhang.search.SearchResultPage
import com.github.tinkzhang.settings.SettingsPage
import com.github.tinkzhang.uicomponent.RkCustomTabClient
import com.github.tinkzhang.wish.WishViewModel
import com.github.tinkzhang.wish.ui.WishListPage
import com.github.tinkzhang.wish.ui.WishVip
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var mCustomTabClient: RkCustomTabClient? = null
    private val generalViewModel: GeneralViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        mCustomTabClient = RkCustomTabClient(this)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                FirebaseRemoteConfigWrapper
                false
            }
        }
        setContent {
            val readingViewModel: ReadingViewModel = viewModel()
            val wishViewModel: WishViewModel = viewModel()
            val archivedViewModel: ArchivedViewModel = viewModel()
            val isDark by generalViewModel.isDark.collectAsState(initial = true)
            ReadKeeperTheme(darkTheme = isDark ?: isSystemInDarkTheme()) {
                val navController = rememberNavController()
                Surface {
                    val route =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    val screen = ROUTE_TO_SCREEN_MAP[route]
                    val scrollBehavior = remember {
                        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec = exponentialDecay())
                    }
                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            if (screen is MainScreenViewData) {
                                RkMainTopBar(
                                    isLogged = UserRepository.loginStatus.observeAsState().value == LoginStatus.Login,
                                    profileUrl = UserRepository.user?.photoUrl.toString(),
                                    onProfileClick = {
                                        navController.navigate(SCREEN_ROUTE.SETTINGS)
                                    },
                                    onSearchClick = {
                                        navController.navigate(SCREEN_ROUTE.SEARCH)
                                    },
                                    scrollBehavior = scrollBehavior
                                )
                            }
                        },
                        bottomBar = {
                            if (screen is MainScreenViewData) {
                                RkNavigationBar(navController = navController)
                            }
                        },
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            NavHost(navController, startDestination = SCREEN_ROUTE.HOME) {
                                composable(SCREEN_ROUTE.HOME) {
                                    Homepage(
                                        navController = navController, viewModel = hiltViewModel()
                                    )
                                }
                                composable(SCREEN_ROUTE.WEEKLY_ITEM) {
                                    val parentEntry = remember {
                                        navController.getBackStackEntry(SCREEN_ROUTE.HOME)
                                    }
                                    val parentViewModel =
                                        hiltViewModel<WeeklyBookViewModel>(parentEntry)
                                    WeeklyBookVIP(
                                        title = it.arguments?.getString("title") ?: "",
                                        viewModel = parentViewModel,
                                        navController = navController,
                                    )
                                }
                                composable(SCREEN_ROUTE.SEARCH) {
                                    SearchPage(
                                        navController = navController,
                                    )
                                }
                                composable(SCREEN_ROUTE.SEARCH_RESUTL) {
                                    SearchResultPage(
                                        it.arguments?.getString("keyword") ?: "",
                                        navController = navController
                                    )
                                }
                                composable(SCREEN_ROUTE.WISH_LIST) {
                                    WishListPage(
                                        wishViewModel = wishViewModel, navController
                                    )
                                }
                                composable(
                                    SCREEN_ROUTE.WISH_ITEM, arguments = listOf(navArgument("uuid") {
                                        type = NavType.StringType
                                    })
                                ) {
                                    WishVip(
                                        uuid = it.arguments?.getString("uuid") ?: "",
                                        wishViewModel = wishViewModel,
                                        navController = navController,
                                        client = mCustomTabClient,
                                    )
                                }
                                composable(SCREEN_ROUTE.READING_LIST) {
                                    ReadingListPage(readingViewModel, navController = navController)
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
                                    ReadingVip(
                                        it.arguments?.getString("uuid") ?: "",
                                        it.arguments?.getBoolean("open_progress_dialog") ?: false,
                                        it.arguments?.getBoolean("open_edit_dialog") ?: false,
                                        readingViewModel,
                                        navController = navController
                                    )
                                }
                                composable(SCREEN_ROUTE.ARCHIVED_LIST) {
                                    ArchivedListPage(archivedViewModel, navController)
                                }
                                composable(
                                    SCREEN_ROUTE.ARCHIVED_ITEM,
                                    arguments = listOf(navArgument("uuid") {
                                        type = NavType.StringType
                                    })
                                ) {
                                    ArchivedVip(
                                        uuid = it.arguments?.getString("uuid") ?: "",
                                        archivedViewModel = archivedViewModel,
                                        navController = navController
                                    )
                                }
                                composable(SCREEN_ROUTE.SETTINGS) {
                                    SettingsPage(
                                        settingsViewModel = hiltViewModel(),
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                UserRepository.firebaseAuthWithGoogle(account.idToken!!, this)
                Timber.d("Google Sign in succeed: \n ${account.email}")
            } catch (e: Exception) {
                Timber.w("Google Sign in failed: \n $e")
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }

    override fun onDestroy() {
        mCustomTabClient?.destroy()
        mCustomTabClient = null
        super.onDestroy()
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

package app.tinks.readkeeper

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.tinks.readkeeper.basic.LoginStatus
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.navigation.MainScreenViewData
import app.tinks.readkeeper.navigation.ROUTE_TO_SCREEN_MAP
import app.tinks.readkeeper.navigation.RkNavHost
import app.tinks.readkeeper.navigation.ui.RkNavigationBar
import app.tinks.readkeeper.ui.components.RkMainTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(navController: NavHostController) {
    Surface {
        val route =
            navController.currentBackStackEntryAsState().value?.destination?.route
        val screen = ROUTE_TO_SCREEN_MAP[route]
        Scaffold(
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
                RkNavHost(navController = navController)
            }
        }
    }
}
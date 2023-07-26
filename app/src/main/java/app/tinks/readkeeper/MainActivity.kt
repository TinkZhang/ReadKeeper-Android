package app.tinks.readkeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.tinks.readkeeper.basic.LoginStatus
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import app.tinks.readkeeper.navigation.MainScreenViewData
import app.tinks.readkeeper.navigation.ROUTE_TO_SCREEN_MAP
import app.tinks.readkeeper.navigation.RkNavHost
import app.tinks.readkeeper.navigation.ui.RkNavigationBar
import app.tinks.readkeeper.readkeeper.ui.theme.ReadKeeperTheme
import app.tinks.readkeeper.ui.components.RkMainTopBar
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import timber.log.Timber

@ExperimentalMaterial3Api
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
            val isDark by generalViewModel.isDark.collectAsState(initial = true)
            ReadKeeperTheme(darkTheme = isDark ?: isSystemInDarkTheme()) {
                val navController = rememberNavController()
                setStatusBar(isDark)
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
        }
    }

    @Composable
    private fun setStatusBar(isDark: Boolean?) {
        val systemUiController = rememberSystemUiController()
        val color = MaterialTheme.colorScheme.background
        val isDarkBar = isDark ?: isSystemInDarkTheme()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = color,
                darkIcons = !isDarkBar
            )
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

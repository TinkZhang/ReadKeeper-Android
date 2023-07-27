package app.tinks.readkeeper

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.firebaseRemoteConfig.FirebaseRemoteConfigWrapper
import app.tinks.readkeeper.readkeeper.ui.theme.ReadKeeperTheme
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
                SetStatusBar(isDark)
                MyApp(navController)
            }
        }
    }

    @Composable
    private fun SetStatusBar(isDark: Boolean?) {
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

package app.tinks.readkeeper.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.DataStoreKey
import app.tinks.readkeeper.basic.LoginStatus
import app.tinks.readkeeper.basic.SCREEN_ROUTE
import app.tinks.readkeeper.settings.model.SettingAttribute
import app.tinks.readkeeper.settings.model.SingleSelectionItem
import app.tinks.readkeeper.settings.section.AboutContent
import app.tinks.readkeeper.settings.section.FeedbackContent
import app.tinks.readkeeper.settings.section.GeneralContent
import app.tinks.readkeeper.settings.section.HomepageContent
import app.tinks.readkeeper.settings.section.LoginContent
import app.tinks.readkeeper.settings.section.LoginErrorContent
import app.tinks.readkeeper.settings.section.LoginLoadingContent
import app.tinks.readkeeper.settings.section.ProfileAndLogoutSection
import app.tinks.readkeeper.settings.ui.RadioSettingDialog
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SettingsPage(
    settingsViewModel: SettingsViewModel = viewModel(),
    navController: NavController? = null
) {
    Scaffold(topBar = {
        SmallTopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = {
                    navController?.popBackStack(
                        SCREEN_ROUTE.HOME,
                        false
                    )
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            },
        )
    }) { padding ->
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        val theme by settingsViewModel.themeStatus.collectAsState(initial = ThemeStatus.DEFAULT)
        var shouldShowThemeDialog by remember { mutableStateOf(false) }
        val themeSetting = SingleSelectionItem(
            commonAttribute = SettingAttribute(
                title = stringResource(id = R.string.theme),
                key = DataStoreKey.THEME,
                icon = Icons.Default.Palette,
            ),
            options = ThemeStatus.values().map { it.label },
        )

        val isQuoteEnable by settingsViewModel.isQuoteEnabled.collectAsState(initial = true)

        val loginStatus by settingsViewModel.loginStatus.observeAsState()

        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                when (loginStatus) {
                    LoginStatus.Login -> ProfileAndLogoutSection(
                        profileImageUrl = settingsViewModel.profileImageUrl,
                        username = settingsViewModel.username,
                        userEmail = settingsViewModel.userEmail,
                        onLogoutClick = settingsViewModel::signOut
                    )

                    LoginStatus.Logging -> LoginLoadingContent()
                    LoginStatus.Error -> LoginErrorContent(onLoginClick = {
                        settingsViewModel.signIn(
                            context
                        )
                    })

                    else -> LoginContent(onLoginClick = { settingsViewModel.signIn(context) })
                }
                Divider(Modifier.padding(top = 16.dp))
                GeneralContent(
                    themeSetting = themeSetting,
                    theme = theme,
                    onThemeSettingClick = { shouldShowThemeDialog = true }
                )
                HomepageContent(
                    isQuoteEnabled = isQuoteEnable,
                    onQuoteSettingClick = {
                        scope.launch {
                            settingsViewModel.saveSetting(
                                DataStoreKey.IS_QUOTE_ENABLE,
                                !isQuoteEnable
                            )
                        }
                    }
                )
                FeedbackContent(context = context)
                AboutContent(context = context)
                Spacer(Modifier.height(16.dp))
            }

            if (shouldShowThemeDialog) {
                RadioSettingDialog(
                    options = ThemeStatus.values(),
                    title = themeSetting.commonAttribute.title,
                    selectedItem = theme,
                    onItemSelect = { value ->
                        scope.launch {
                            settingsViewModel.saveSetting(
                                themeSetting.commonAttribute.key,
                                value.name.uppercase()
                            )
                        }
                        shouldShowThemeDialog = false
                    },
                    onDismiss = { shouldShowThemeDialog = false })
            }
        }
    }
}

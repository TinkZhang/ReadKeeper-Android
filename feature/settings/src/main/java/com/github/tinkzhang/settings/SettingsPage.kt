package com.github.tinkzhang.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.github.readkeeper.instabug.InstabugWrapper
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.settings.model.*
import com.github.tinkzhang.settings.ui.RadioSettingDialog
import com.github.tinkzhang.settings.ui.SettingItemCell
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SettingsPage(
    settingsViewModel: SettingsViewModel,
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
    }) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        val theme by settingsViewModel.themeStatus.collectAsState(initial = ThemeStatus.DEFAULT)
        var shouldShowThemeDialog by remember { mutableStateOf(false) }
        val themeSetting = SingleSelectionItem(
            commonAttribute = SettingAttribute(
                title = "Theme",
                key = "theme",
                icon = Icons.Default.Palette,
            ),
            options = ThemeStatus.values().map { it.label },
        )

        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // General
                Text(
                    "General",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                SettingItemCell(
                    item = themeSetting,
                    label = theme.label,
                    onClick = {
                        shouldShowThemeDialog = true
                    }
                )
                Divider(Modifier.padding(4.dp))
                // Feedback
                Text(
                    "Feedback",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                SettingItemCell(
                    item = OpenPageItem(
                        commonAttribute = SettingAttribute(
                            title = "Bug report & Suggestion",
                            icon = Icons.Default.BugReport
                        ),
                    ),
                    onClick = { InstabugWrapper.show() }
                )
                SettingItemCell(
                    item = ExternalPageItem(
                        commonAttribute = SettingAttribute(
                            title = "Rate on Play Store",
                            icon = Icons.Default.ThumbUp
                        ),
                    ),
                    label = "Please give me 5 stars. \uD83D\uDE18",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://play.google.com/store/apps/details?id=com.ebay.gumtree.au")
                        }
                        startActivity(context, intent, null)
                    }
                )
                Divider(Modifier.padding(4.dp))

                // About
                Text(
                    "About",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                SettingItemCell(
                    item = ExternalPageItem(
                        commonAttribute = SettingAttribute(
                            title = "Release notes",
                            icon = Icons.Default.RssFeed
                        ),
                    ),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://github.com/TinkZhang/ReadKeeper-Android/releases")
                        }
                        startActivity(context, intent, null)
                    }
                )
                SettingItemCell(
                    item = ExternalPageItem(
                        commonAttribute = SettingAttribute(
                            title = "App code on Github",
                            icon = Icons.Default.Code
                        ),
                    ),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data =
                                Uri.parse("https://github.com/TinkZhang/ReadKeeper-Android")
                        }
                        startActivity(context, intent, null)
                    }
                )
                SettingItemCell(
                    item = StaticItem(
                        commonAttribute = SettingAttribute(
                            title = "App version",
                            key = "version",
                            icon = Icons.Default.Info
                        ),
                    ),
                    label = "1.0.0"
                )
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

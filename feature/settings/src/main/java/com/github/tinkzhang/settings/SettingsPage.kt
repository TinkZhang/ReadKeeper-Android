package com.github.tinkzhang.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.settings.model.SettingAttribute
import com.github.tinkzhang.settings.model.SingleSelectionItem
import com.github.tinkzhang.settings.section.AboutContent
import com.github.tinkzhang.settings.section.FeedbackContent
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

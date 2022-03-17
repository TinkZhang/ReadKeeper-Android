package com.github.tinkzhang.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.settings.model.SettingType
import com.github.tinkzhang.settings.ui.RadioSettingDialog
import com.github.tinkzhang.settings.ui.SettingSummary
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
        val items = settingsViewModel.settingSummaryItems
        val scope = rememberCoroutineScope()
        val theme by settingsViewModel.themeStatus.collectAsState(initial = ThemeStatus.DEFAULT)
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(items) { item ->
                        SettingSummary(
                            item,
                            label = theme.label,
                            onSettingItemClick = { settingsViewModel.displayedDialogKey = it })
                    }
                }
            }
            if (settingsViewModel.displayedDialogKey.isNotEmpty()) {
                val displayedSettingItem =
                    settingsViewModel.getSettingItem(settingsViewModel.displayedDialogKey)
                displayedSettingItem?.let {
                    when (it.type) {
                        is SettingType.MultipleSelection -> TODO()
                        is SettingType.NewPage -> TODO()
                        is SettingType.SingleSelection -> RadioSettingDialog(
                            options = ThemeStatus.values(),
                            title = it.title,
                            selectedItem = theme,
                            onItemSelect = { value ->
                                scope.launch {
                                    settingsViewModel.saveSetting(
                                        it.key,
                                        value.name.uppercase()
                                    )
                                }
                                settingsViewModel.displayedDialogKey = ""
                            },
                            onDismiss = { settingsViewModel.displayedDialogKey = "" })
                        is SettingType.Toggle -> TODO()
                        is SettingType.WebLink -> TODO()
                    }
                }
            }
        }
    }
}

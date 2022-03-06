package com.github.tinkzhang.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.settings.ui.SettingSummary

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
        val items = settingsViewModel.test.toMutableStateList()
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            LazyColumn {
                items(items) { item ->
                    SettingSummary(
                        title = item.title,
                        subtitle = item.subtitle,
                        isExpanded = item.isExpanded,
                        onClick = { settingsViewModel.toggleExpandedState(item.title) })
                }
            }
        }
    }
}

package com.github.tinkzhang.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.github.tinkzhang.basic.SCREEN_ROUTE

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
        Text("Hello World")
    }
}
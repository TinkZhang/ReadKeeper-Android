package app.github.tinkzhang.uicomponent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RkBackTopBar(title: String, navController: NavController) {
    SmallTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Backup,
                contentDescription = "Back"
            )
        },
    )
}
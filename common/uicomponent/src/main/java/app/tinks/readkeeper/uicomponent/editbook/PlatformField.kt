package app.tinks.readkeeper.uicomponent.editbook

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Platform
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun PlatformField(
    modifier: Modifier = Modifier,
    platform: Platform? = null,
    onPlatformChange: (Platform) -> Unit = {}
) {
    var selectedPlatform by remember(platform) { mutableStateOf(platform) }
    var isValid by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedPlatform?.name ?: "",
            onValueChange = { isValid = selectedPlatform != null },
            isError = !isValid,
            readOnly = true,
            modifier = modifier.clickable { expanded = true },
            label = { Text(text = stringResource(id = R.string.platform)) },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.NavigateNext, null)
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            modifier = modifier.padding(horizontal = 16.dp),
            onDismissRequest = { expanded = false }
        ) {
            Platform.values().forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        selectedPlatform = it
                        expanded = false
                        onPlatformChange(it)
                    },
                    leadingIcon = {
                        Icon(
                            painterResource(id = it.icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    },
                    trailingIcon = {
                        if (it != selectedPlatform) return@DropdownMenuItem
                        Icon(Icons.Default.Check, contentDescription = null)
                    }
                )
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun PlatformField() {
    ReadKeeperTheme {
        Surface {
            PlatformField(platform = Platform.PDF)
        }
    }
}

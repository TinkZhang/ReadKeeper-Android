package app.github.tinkzhang.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.github.tinkzhang.settings.ThemeStatus

@Composable
fun SettingDialog(
    title: String = "",
    onDismiss: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(title) },
        text = content
    )
}

@ExperimentalMaterial3Api
@Composable
fun RadioSettingDialog(
    title: String = "",
    selectedItem: ThemeStatus,
    options: Array<ThemeStatus>,
    onItemSelect: (ThemeStatus) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    SettingDialog(title = title, onDismiss = onDismiss) {
        var value: ThemeStatus by remember { mutableStateOf(selectedItem) }
        Column(Modifier.fillMaxWidth()) {
            options.forEach { option ->
                Row(verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .clickable { onItemSelect(option) }
                        .fillMaxWidth()
                ) {
                    RadioButton(
                        selected = option == value,
                        onClick = {
                            value = option
                            onItemSelect(option)
                        })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = option.label)
                }
            }
        }
    }
}

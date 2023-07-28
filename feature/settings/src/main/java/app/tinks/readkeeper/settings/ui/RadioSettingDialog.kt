package app.tinks.readkeeper.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.settings.R
import app.tinks.readkeeper.settings.ThemeStatus

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
                Text(stringResource(id = R.string.cancel))
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
                    Text(text = stringResource(id = option.label))
                }
            }
        }
    }
}

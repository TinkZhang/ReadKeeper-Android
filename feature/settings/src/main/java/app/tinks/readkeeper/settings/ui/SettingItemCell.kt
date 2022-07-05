package app.tinks.readkeeper.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.settings.model.*

@Composable
fun SettingItemCell(
    item: SettingItem,
    label: String? = null,
    onClick: (SettingItem) -> Unit = {}
) {
    when (item) {
        is SingleSelectionItem -> SettingItemCell(
            title = item.commonAttribute.title,
            subtitle = label,
            icon = item.commonAttribute.icon,
            onClick = { onClick(item) })
        is StaticItem -> SettingItemCell(
            title = item.commonAttribute.title,
            subtitle = label,
            icon = item.commonAttribute.icon
        )
        is OpenPageItem -> SettingItemCell(
            title = item.commonAttribute.title,
            subtitle = label,
            icon = item.commonAttribute.icon,
            onClick = { onClick(item) },
            right = { Icon(Icons.Default.ChevronRight, contentDescription = null) }
        )
        is ExternalPageItem -> SettingItemCell(
            title = item.commonAttribute.title,
            subtitle = label,
            icon = item.commonAttribute.icon,
            onClick = { onClick(item) },
            right = { Icon(Icons.Default.OpenInNew, contentDescription = null) }
        )
        is ToggleItem -> SettingItemCell(
            title = item.commonAttribute.title,
            onClick = { onClick(item) },
            right = { Switch(checked = item.value, onCheckedChange = { onClick(item)}) }
        )
    }
}

@Composable
fun SettingItemCell(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    onClick: () -> Unit = {},
    right: @Composable () -> Unit = {}
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            if (icon != null) {
                Icon(
                    icon,
                    null,
                    tint = LocalContentColor.current.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                if (!subtitle.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
        right()
    }
}
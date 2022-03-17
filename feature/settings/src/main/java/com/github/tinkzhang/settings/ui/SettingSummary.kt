package com.github.tinkzhang.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.mockStringMedium
import com.github.tinkzhang.settings.model.SettingSummaryItem
import com.github.tinkzhang.settings.model.mockSettingSummaryItem

@Composable
fun SettingSummary(
    settingSummaryItem: SettingSummaryItem,
    label: String,
    onSettingItemClick: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(settingSummaryItem.title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        settingSummaryItem.items.forEach {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSettingItemClick(it.key)
                    }
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    it.icon,
                    null,
                    tint = LocalContentColor.current.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(text = it.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview
@Composable
private fun SettingSummaryPreview() {
    SettingSummary(mockSettingSummaryItem(), label = mockStringMedium)
}

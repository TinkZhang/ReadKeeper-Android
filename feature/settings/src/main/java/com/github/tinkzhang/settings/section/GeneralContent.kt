package com.github.tinkzhang.settings.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.settings.ThemeStatus
import com.github.tinkzhang.settings.model.SettingItem
import com.github.tinkzhang.settings.themeSetting
import com.github.tinkzhang.settings.ui.SettingItemCell

@Composable
fun GeneralContent(
    theme: ThemeStatus,
    onThemeSettingClick: (SettingItem) -> Unit = {}
) {
    Column {
        Text(
            "General",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        SettingItemCell(
            item = themeSetting,
            label = theme.label,
            onClick = onThemeSettingClick
        )
        Divider(Modifier.padding(4.dp))
    }
}

@Preview
@Composable
private fun GeneralContentPreview() {
    GeneralContent(theme = ThemeStatus.DEFAULT)
}

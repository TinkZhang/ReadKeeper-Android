package app.tinks.readkeeper.settings.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.settings.ThemeStatus
import app.tinks.readkeeper.settings.model.SettingAttribute
import app.tinks.readkeeper.settings.model.SettingItem
import app.tinks.readkeeper.settings.model.SingleSelectionItem
import app.tinks.readkeeper.settings.ui.SettingItemCell
import app.tinks.readkeeper.basic.DataStoreKey
import app.tinks.readkeeper.settings.R

@Composable
fun GeneralContent(
    themeSetting: SettingItem,
    theme: ThemeStatus,
    onThemeSettingClick: (SettingItem) -> Unit = {}
) {
    Column {
        Text(
            stringResource(id = R.string.general_setting_title),
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
    GeneralContent(
        themeSetting = SingleSelectionItem(
            commonAttribute = SettingAttribute(
                title = stringResource(id = R.string.theme),
                key = DataStoreKey.THEME,
                icon = Icons.Default.Palette,
            ),
            options = ThemeStatus.values().map { it.label },
        ), theme = ThemeStatus.DEFAULT
    )
}

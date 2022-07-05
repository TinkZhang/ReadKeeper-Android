package app.github.tinkzhang.settings.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.github.tinkzhang.settings.model.SettingAttribute
import app.github.tinkzhang.settings.model.SettingItem
import app.github.tinkzhang.settings.model.ToggleItem
import app.github.tinkzhang.settings.ui.SettingItemCell
import com.github.tinkzhang.settings.R

@Composable
fun HomepageContent(
    isExpanded: Boolean = false,
    isQuoteEnabled: Boolean = true,
    onQuoteSettingClick: (SettingItem) -> Unit = {},
) {
    var isHomepageExpanded by remember {
        mutableStateOf(isExpanded)
    }

    if (!isHomepageExpanded) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isHomepageExpanded = true }
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    stringResource(id = R.string.homepage_setting_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.homepage_setting_subtitle),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.W400
                )
            }
            Icon(Icons.Default.ExpandMore, null)
        }
    } else {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isHomepageExpanded = false },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Homepage Content",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.W400
                    )
                }
                Icon(Icons.Default.ExpandLess, null)
            }
            SettingItemCell(
                item = ToggleItem(
                    commonAttribute = SettingAttribute(
                        title = stringResource(id = R.string.quote_setting_title),
                        icon = Icons.Default.BugReport
                    ),
                    isQuoteEnabled
                ),
                onClick = onQuoteSettingClick
            )
        }
    }
    Divider(Modifier.padding(4.dp))
}
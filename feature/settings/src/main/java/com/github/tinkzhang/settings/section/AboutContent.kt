package com.github.tinkzhang.settings.section

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.github.tinkzhang.settings.model.ExternalPageItem
import com.github.tinkzhang.settings.model.SettingAttribute
import com.github.tinkzhang.settings.model.StaticItem
import com.github.tinkzhang.settings.ui.SettingItemCell


@Composable
fun AboutContent(isExpanded: Boolean = false, context: Context? = null) {
    var isAboutExpanded by remember {
        mutableStateOf(isExpanded)
    }

    if (!isAboutExpanded) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isAboutExpanded = true }
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "About",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Version, release notes, source code",
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
                    .clickable { isAboutExpanded = false },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "About",
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
                item = ExternalPageItem(
                    commonAttribute = SettingAttribute(
                        title = "Release notes",
                        icon = Icons.Default.RssFeed
                    ),
                ),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://github.com/TinkZhang/ReadKeeper-Android/releases")
                    }
                    if (context != null) {
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
            SettingItemCell(
                item = ExternalPageItem(
                    commonAttribute = SettingAttribute(
                        title = "App code on Github",
                        icon = Icons.Default.Code
                    ),
                ),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://github.com/TinkZhang/ReadKeeper-Android")
                    }
                    if (context != null) {
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
            SettingItemCell(
                item = StaticItem(
                    commonAttribute = SettingAttribute(
                        title = "App version",
                        key = "version",
                        icon = Icons.Default.Info
                    ),
                ),
                label = "1.0.0"
            )
        }
    }
    Divider(Modifier.padding(4.dp))
}

@Preview
@Composable
private fun AboutContentCollapsedPreview() {
    AboutContent(isExpanded = false)
}

@Preview
@Composable
private fun AboutContentExpandedPreview() {
    AboutContent(isExpanded = true)
}

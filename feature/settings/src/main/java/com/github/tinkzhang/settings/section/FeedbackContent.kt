package com.github.tinkzhang.settings.section

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ThumbUp
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
import com.github.readkeeper.instabug.InstabugWrapper
import com.github.tinkzhang.settings.model.ExternalPageItem
import com.github.tinkzhang.settings.model.OpenPageItem
import com.github.tinkzhang.settings.model.SettingAttribute
import com.github.tinkzhang.settings.ui.SettingItemCell


@Composable
fun FeedbackContent(isExpanded: Boolean = false, context: Context? = null) {
    var isFeedbackExpanded by remember {
        mutableStateOf(isExpanded)
    }

    if (!isFeedbackExpanded) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isFeedbackExpanded = true }
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Feedback",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Help to make the App better",
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
                    .clickable { isFeedbackExpanded = false },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Feedback",
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
                item = OpenPageItem(
                    commonAttribute = SettingAttribute(
                        title = "Bug report & Suggestion",
                        icon = Icons.Default.BugReport
                    ),
                ),
                onClick = { InstabugWrapper.show() }
            )
            SettingItemCell(
                item = ExternalPageItem(
                    commonAttribute = SettingAttribute(
                        title = "Rate on Play Store",
                        icon = Icons.Default.ThumbUp
                    ),
                ),
                label = "Please give me 5 stars. \uD83D\uDE18",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            Uri.parse("https://play.google.com/store/apps/details?id=com.ebay.gumtree.au")
                    }
                    if (context != null) {
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
        }
    }
    Divider(Modifier.padding(4.dp))
}

@Preview
@Composable
private fun FeedbackContentCollapsedPreview() {
    FeedbackContent()
}

@Preview
@Composable
private fun FeedbackContentExpandedPreview() {
    FeedbackContent(isExpanded = true)
}

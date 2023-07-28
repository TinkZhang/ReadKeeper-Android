package app.tinks.readkeeper.settings.section

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import app.tinks.readkeeper.settings.R
import app.tinks.readkeeper.settings.model.ExternalPageItem
import app.tinks.readkeeper.settings.model.SettingAttribute
import app.tinks.readkeeper.settings.ui.SettingItemCell


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
                    stringResource(id = R.string.feedback),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.feedback_explain),
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
                        stringResource(id = R.string.feedback),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.W400
                    )
                }
                Icon(Icons.Default.ExpandLess, null)
            }
            SettingItemCell(
                item = ExternalPageItem(
                    commonAttribute = SettingAttribute(
                        title = stringResource(id = R.string.bug_report),
                        icon = Icons.Default.BugReport
                    ),
                ),
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:tink.readkeeper@gmail.com?subject=Feedback for ReadKeeper")
                    }
                    if (context!= null) {
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
            SettingItemCell(
                item = ExternalPageItem(
                    commonAttribute = SettingAttribute(
                        title = stringResource(id = R.string.rate_on_store),
                        icon = Icons.Default.ThumbUp
                    ),
                ),
                label = stringResource(id = R.string.give_5_star),
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

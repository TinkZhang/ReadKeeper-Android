package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.firebaseRemoteConfig.SearchEngine
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchEngineButton(
    link: String,
    label: String,
    client: RkCustomTabClient?,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = { client?.launchTab(link) },
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(64.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(32.dp))
                Text(label)
            }
            Icon(
                Icons.Default.Launch,
                contentDescription = null,
                Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun SearchEngineButton(
    title: String,
    searchEngine: SearchEngine,
    client: RkCustomTabClient?,
    modifier: Modifier = Modifier,
) {
    SearchEngineButton(
        // TODO: the link generation rule could be different between different search engine
        link = searchEngine.link + title,
        label = searchEngine.name,
        client = client,
        modifier = modifier
    )
}

@PreviewAnnotation
@Composable
private fun SearchEngineButton() {
    ReadKeeperTheme {
        Surface {
            SearchEngineButton(
                title = "Hello",
                searchEngine = SearchEngine("Google", "www.google.com"),
                client = null
            )
        }
    }
}

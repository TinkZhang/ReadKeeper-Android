package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.firebaseRemoteConfig.SearchEngine
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import app.tinks.readkeeper.uicomponent.cellview.SearchEngineButton
import app.tinks.readkeeper.uicomponent.detail.actionsection.DetailTitleSection
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@ExperimentalMaterial3Api
@Composable
fun GetBookSection(
    title: String,
    searchEngines: List<SearchEngine>?,
    client: RkCustomTabClient?,
    modifier: Modifier = Modifier,
    amazonLink: String? = null,
) {
    DetailTitleSection(
        title = stringResource(id = R.string.get_book_section_title),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (!amazonLink.isNullOrEmpty()) {
                SearchEngineButton(
                    link = amazonLink,
                    label = stringResource(id = R.string.amazon),
                    client = client
                )
            }
            searchEngines?.forEach {
                SearchEngineButton(title = title, searchEngine = it, client)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewAnnotation
@Composable
private fun GetBookSectionPreview() {
    ReadKeeperTheme {
        Surface {
            GetBookSection(
                title = "Hello",
                amazonLink = "www.amazon.com",
                searchEngines = listOf(
                    SearchEngine("Google", "www.google.com"),
                    SearchEngine("Baidu", "www.baidu.com")
                ), client = null
            )
        }
    }
}

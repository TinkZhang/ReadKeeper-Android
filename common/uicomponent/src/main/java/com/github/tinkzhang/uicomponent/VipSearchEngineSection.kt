package com.github.tinkzhang.uicomponent

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.firebaseRemoteConfig.SearchEngine

@ExperimentalMaterial3Api
@Composable
fun VipSearchEngineSection(
    title: String,
    searchEngines: List<SearchEngine>,
    client: RkCustomTabClient?
) {
    Section(title = "Find the Book") {
        searchEngines.forEach {
            Spacer(modifier = Modifier.height(8.dp))
            SearchEngineButton(title = title, searchEngine = it, client)
        }
    }
}

@Composable
fun SearchEngineButton(title: String, searchEngine: SearchEngine, client: RkCustomTabClient?) {
    val content = LocalContext.current
    val uri = Uri.parse(searchEngine.link + Uri.encode(title))
    OutlinedButton(
        onClick = {
//            startActivity(
//                content,
//                Intent(Intent.ACTION_VIEW, uri),
//                null
//            )
                  client?.launchTab(searchEngine.link + title)
        },
        Modifier
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
                Text(searchEngine.name)
            }

            Icon(
                Icons.Default.Launch,
                contentDescription = null,
                Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
private fun SearchEngineButtonPreview() {
    SearchEngineButton(
        title = "Hello",
        searchEngine = SearchEngine("Google", "www.google.com"),
        client = null
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun VipSearchEngineSectionPreview() {
    VipSearchEngineSection(
        title = "Hello", searchEngines = listOf(
            SearchEngine("Google", "www.google.com"),
            SearchEngine("Baidu", "www.baidu.com")
        ), client = null
    )
}
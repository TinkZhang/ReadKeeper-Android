package com.github.tinkzhang.wish.ui.components

import androidx.compose.foundation.layout.*
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
import com.github.tinkzhang.firebaseRemoteConfig.SearchEngine

@Composable
fun VipSearchEngineSection(title: String, searchEngines: List<SearchEngine>) {
    Surface(tonalElevation = 4.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            searchEngines.forEach { 
                Spacer(modifier = Modifier.height(8.dp))
                SearchEngineButton(title = title, searchEngine = it)
            }
        }
    }
}

@Composable
fun SearchEngineButton(title: String, searchEngine: SearchEngine) {
    OutlinedButton(onClick = { /*TODO*/ },
        Modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)) {
        Icon(Icons.Default.Search, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(searchEngine.name)
        Icon(Icons.Default.Launch, contentDescription = null, Modifier.align(Alignment.CenterVertically))
    }
}
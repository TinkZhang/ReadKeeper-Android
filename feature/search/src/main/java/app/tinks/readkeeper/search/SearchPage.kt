package app.tinks.readkeeper.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.tinks.readkeeper.search.ui.components.SearchResultItem
import app.tinks.readkeeper.search.ui.components.SearchTextField
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

val testSearchItems = listOf("Kotlin in action", "Jetpack Compose", "Thinking in Java")

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    onHistoryItemClick: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: SearchViewModel = viewModel()
) {
    SearchPage(
        historyItems = viewModel.searchHistory.collectAsState(initial = emptyList()).value,
        onHistoryItemClick = onHistoryItemClick,
        onSearch = {
            onSearch(it)
            viewModel.addSearchHistory(it)
        },
        onBackClick = onBackClick
    )
}

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    historyItems: List<String>,
    onSearch: (String) -> Unit = {},
    onHistoryItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            SearchTextField(
                onSearch = onSearch,
                placeHolderText = R.string.home_search_bar
            )
        }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Divider()
            LazyColumn {
                items(historyItems) {
                    SearchResultItem(text = it,
                        image = Icons.Default.History,
                        onClick = { onHistoryItemClick(it) })
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@PreviewAnnotation
@Composable
private fun SearchPagePreview() {
    ReadKeeperTheme {
        Surface {
            SearchPage(historyItems = testSearchItems)
        }
    }
}

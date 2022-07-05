package app.github.tinkzhang.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.github.tinkzhang.search.ui.components.SearchResultItem
import app.github.tinkzhang.search.ui.components.SearchTextField
import app.github.tinkzhang.uicomponent.PreviewAnnotation
import app.github.tinkzhang.uicomponent.theme.ReadKeeperTheme
import com.github.tinkzhang.uicomponent.R

val testSearchItems = listOf("Kotlin in action", "Jetpack Compose", "Thinking in Java")

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    onHistoryItemClick: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val viewModel: SearchViewModel = hiltViewModel()
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
        SmallTopAppBar(title = {
            SearchTextField(
                onSearch = onSearch, placeHolderText = R.string.home_search_bar
            )
        }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
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

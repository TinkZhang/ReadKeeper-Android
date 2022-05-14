package com.github.tinkzhang.search

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.tinkzhang.search.ui.components.SearchResultItem
import com.github.tinkzhang.search.ui.components.SearchTextField
import com.github.tinkzhang.uicomponent.PreviewAnnotation
import com.github.tinkzhang.uicomponent.theme.ReadKeeperTheme

val testSearchItems = listOf("Kotlin in action", "Jetpack Compose", "Thinking in Java")

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()
) {
    val historyItems by searchViewModel.searchHistory.collectAsState(initial = listOf())
    SearchPage(historyItems = historyItems, onSearch = { keyword ->
        navController.navigate("search_result/${keyword}")
        searchViewModel.addSearchHistory(keyword)
    }, onHistoryItemClick = { item ->
        navController.navigate("search_result/${item}")
    }, onBackClick = { navController.popBackStack() })
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

package com.github.tinkzhang.search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.github.tinkzhang.search.ui.components.SearchResultItem
import com.github.tinkzhang.search.ui.components.SearchTextField

val testSearchItems = listOf("Kotlin in action", "Jetpack Compose", "Thinking in Java")

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val historyItems by searchViewModel.searchHistory.collectAsState(initial = listOf())
    SearchPage(
        historyItems = historyItems,
        onHistoryItemAdd = { searchViewModel.addSearchHistory(it) },
        navController = navController
    )
}

@ExperimentalMaterial3Api
@Composable
fun SearchPage(
    historyItems: List<String>,
    onHistoryItemAdd: (String) -> Unit = {},
    navController: NavController? = null,
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = {
                SearchTextField(
                    onSearch = { keyword ->
                        navController?.navigate("search_result/${keyword}")
                        onHistoryItemAdd(keyword)
                    }
                )
            }, navigationIcon = {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        }
    ) {
        LazyColumn() {
            items(historyItems) {
                SearchResultItem(
                    text = it,
                    image = Icons.Default.History,
                    onClick = { navController?.navigate("search_result/${it}") }
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun SearchPagePreview() {
    MaterialTheme {
        SearchPage(historyItems = testSearchItems)
    }
}


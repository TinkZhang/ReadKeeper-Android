package com.github.tinkzhang.search

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.github.tinkzhang.search.ui.components.SearchResultItem
import com.github.tinkzhang.search.ui.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun SearchPage(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val historyItems by searchViewModel.searchHistory.collectAsState(initial = listOf())
    Scaffold(
        topBar = {
            SmallTopAppBar(title = {
                SearchTextField(
                    onSearch = { keyword ->
                        navController.navigate("search_result/${keyword}")
                        searchViewModel.addSearchHistory(keyword)
                    }
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        }
    ) {
        historyItems.forEach {
            SearchResultItem(
                text = it,
                image = Icons.Default.History,
                onClick = { navController.navigate("search_result/${it}") })
        }
    }
}


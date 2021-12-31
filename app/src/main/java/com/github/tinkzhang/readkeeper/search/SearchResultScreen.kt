package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.readkeeper.search.SearchListCard
import com.github.tinkzhang.readkeeper.search.SearchResultViewModel
import com.github.tinkzhang.readkeeper.search.SearchResultViewModelFactory
import com.github.tinkzhang.readkeeper.search.components.RkSearchErrorItem
import com.github.tinkzhang.readkeeper.search.components.RkSearchTipItem
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(keyword: String, navController: NavController? = null) {
    val searchResultViewModel: SearchResultViewModel = viewModel(
        factory = SearchResultViewModelFactory(keyword)
    )
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        keyword,
                        Modifier
                            .fillMaxWidth()
                            .clickable { navController?.popBackStack() })
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController?.popBackStack(
                            SCREEN_ROUTE.HOME,
                            false
                        )
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        },
    ) {
        val books = searchResultViewModel.flow.collectAsLazyPagingItems()
        LazyColumn {
            when {
                books.loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
                books.loadState.refresh is LoadState.Error -> {
                    item {
                        RkSearchErrorItem((books.loadState.refresh as LoadState.Error).error)
                    }
                }
                books.loadState.append is LoadState.Error -> {
                    item {
                        RkSearchErrorItem((books.loadState.refresh as LoadState.Error).error)
                    }
                }
                books.loadState.refresh is LoadState.NotLoading -> {
                    item {
                        RkSearchTipItem(books.itemCount)
                    }
                }
            }
            itemsIndexed(books) { index, item ->
                if (item != null) {
                    SearchListCard(book = item, searchResultViewModel)
                }
            }

            if (books.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

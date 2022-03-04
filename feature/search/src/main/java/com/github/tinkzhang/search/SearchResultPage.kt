package com.github.tinkzhang.search

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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.search.ui.components.RkSearchErrorItem
import com.github.tinkzhang.search.ui.components.RkSearchTipItem

@ExperimentalMaterial3Api
@Composable
fun SearchResultPage(
    keyword: String,
    searchResultViewModel: SearchResultViewModel,
    navController: NavController? = null
) {
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

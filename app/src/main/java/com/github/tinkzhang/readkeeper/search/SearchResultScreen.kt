package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.readkeeper.search.SearchBookItem
import com.github.tinkzhang.readkeeper.search.SearchResultViewModel
import com.github.tinkzhang.readkeeper.search.SearchResultViewModelFactory
import com.github.tinkzhang.readkeeper.search.components.RkSearchErrorItem
import com.github.tinkzhang.readkeeper.search.components.RkSearchTipItem

@Composable
fun SearchResultScreen(keyword: String) {
    val resultViewModel: SearchResultViewModel = viewModel(
        factory = SearchResultViewModelFactory(keyword)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(keyword) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
        ) {
            val books = resultViewModel.flow.collectAsLazyPagingItems()
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
                        SearchBookItem(book = item)
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
}

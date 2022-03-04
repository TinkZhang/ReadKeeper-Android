package com.github.readkeeper.archived.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.readkeeper.archived.ArchivedViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterial3Api
@Composable
fun ArchivedListPage(
    archivedViewModel: ArchivedViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val books = archivedViewModel.flow.collectAsLazyPagingItems()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = books.loadState.refresh is LoadState.Loading),
            onRefresh = {
                archivedViewModel.resetLocalList()
                books.refresh()
            }) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                when {
                    books.loadState.refresh is LoadState.Error -> {
                        item {
//                            RkSearchErrorItem((books.loadState.refresh as LoadState.Error).error)
                        }
                    }
                    books.loadState.append is LoadState.Error -> {
                        item {
//                            RkSearchErrorItem((books.loadState.refresh as LoadState.Error).error)
                        }
                    }
                    books.loadState.refresh is LoadState.NotLoading -> {
                        item {
//                            RkSearchTipItem(books.itemCount)
                        }
                    }
                }
                itemsIndexed(books) { index, item ->
                    if (item != null) {
                        archivedViewModel.addLocalList(item)
                        ArchivedListCard(item, navController)
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

                item { Spacer(modifier = Modifier.height(96.dp)) }
            }
        }
    }
}
package app.tinks.readkeeper.wish.ui

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
import app.tinks.readkeeper.wish.WishViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterial3Api
@Composable
fun WishListPage(
    wishViewModel: WishViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val books = wishViewModel.flow.collectAsLazyPagingItems()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = books.loadState.refresh is LoadState.Loading),
            onRefresh = {
                wishViewModel.resetLocalList()
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
                itemsIndexed(books, key = { _, item -> item.bookInfo.uuid }) { index, item ->
                    if (item != null) {
                        wishViewModel.addLocalList(item)
                        WishListCard(item, navController) {
                            wishViewModel.moveToReading(item)
                        }
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

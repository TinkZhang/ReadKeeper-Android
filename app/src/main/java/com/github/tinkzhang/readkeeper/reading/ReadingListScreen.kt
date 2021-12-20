package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.search.components.RkSearchErrorItem
import com.github.tinkzhang.readkeeper.search.components.RkSearchTipItem
import com.github.tinkzhang.readkeeper.ui.components.RkCategoryChip
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber

@Composable
fun ReadingListScreen(viewModel: ReadingViewModel, navController: NavController) {
    val selectedCategory = viewModel.selectedCategory.value
    val books: MutableList<ReadingBook> by viewModel.list.observeAsState(mutableListOf())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
//        val scrollState = rememberLazyListState()
        val composableScope = rememberCoroutineScope()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
//            state = scrollState,
        ) {
            val categories = viewModel.categories.value
            if (categories != null) {
                items(categories) { category ->
                    RkCategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            viewModel.onSelectedCategoryChanged(category)
                            viewModel.addBook()
                            Timber.d("${viewModel.list.value?.size}")
                            Timber.d("${books.size}")
//                            viewModel.onChangeCategoryScrollPosition(scrollState)
                        },
                        onExecuteSearch = viewModel::newSearch,
                    )
                }
            }
        }
        val books = viewModel.flow.collectAsLazyPagingItems()
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = books.loadState.refresh is LoadState.Loading),
            onRefresh = { books.refresh() }) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                when {
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
                        ReadingBookListItem(item, navController)
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

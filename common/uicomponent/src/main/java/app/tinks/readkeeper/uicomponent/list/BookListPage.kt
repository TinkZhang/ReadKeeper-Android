package app.tinks.readkeeper.uicomponent.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import app.tinks.readkeeper.basic.BookViewModel
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.uicomponent.cellview.EmptyBookListItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterial3Api
@Composable
fun BookListPage(
    bookViewModel: BookViewModel, status: Status, navController: NavController
) {
    val selectedCategory = bookViewModel.selectedCategory.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val categories = bookViewModel.categories.value
            if (categories != null) {
                items(categories) { category ->
//                    RkCategoryChip(
//                        category = category,
//                        isSelected = selectedCategory == category,
//                        onSelectedCategoryChanged = {
//                            readingViewModel.onSelectedCategoryChanged(category)
//                            Timber.d("${readingViewModel.list.value?.size}")
////                            viewModel.onChangeCategoryScrollPosition(scrollState)
//                        },
//                        onExecuteSearch = readingViewModel::newSearch,
//                    )
                }
            }
        }

        val books = bookViewModel.getListFlow(status).collectAsLazyPagingItems()
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = books.loadState.refresh is LoadState.Loading),
            onRefresh = {
                bookViewModel.sync()
                books.refresh()
            }) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                if (books.itemCount == 0) {
                    item {
                        EmptyBookListItem(modifier = Modifier.fillMaxSize())
                    }
                }
                itemsIndexed(books) { index, item ->
                    if (item != null) {
                        BookListCard(
                            book = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("reading_item/${item.basicInfo.uuid}")
                                },
                            onAddProgressClicked = {
                                navController.navigate("reading_item/${item.basicInfo.uuid}?open_progress_dialog=${true}")
                            },
                        )
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

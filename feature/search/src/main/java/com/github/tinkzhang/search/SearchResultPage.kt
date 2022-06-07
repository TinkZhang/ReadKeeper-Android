package com.github.tinkzhang.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.search.ui.components.RkSearchErrorItem
import com.github.tinkzhang.search.ui.components.RkSearchTipItem
import com.github.tinkzhang.uicomponent.GoogleAdView
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SearchResultPage(
    onTitleClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val viewModel: SearchResultViewModel = hiltViewModel()
    val keyword = viewModel.keyword
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        keyword,
                        Modifier
                            .fillMaxWidth()
                            .clickable { onTitleClick() })
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        val books = viewModel.flow.collectAsLazyPagingItems()
        LazyColumn(
            Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
                        RkSearchErrorItem(
                            (books.loadState.refresh as LoadState.Error).error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    books.retry()
                                })
                    }
                }
                books.loadState.append is LoadState.Error -> {
                    item {
                        RkSearchErrorItem(
                            (books.loadState.refresh as LoadState.Error).error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    books.retry()
                                })
                    }
                }
                books.loadState.refresh is LoadState.NotLoading -> {
                    item {
                        RkSearchTipItem(books.itemCount)
                    }
                }
            }
            itemsIndexed(items = books, key = { _, item -> item.bookInfo.uuid }) { index, item ->
                if (item != null) {
                    SearchListItem(
                        book = item,
                        onAddWishClick = { checked ->
                            if (checked) {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.added_into_wish,
                                        item.bookInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                    dismissAction = { viewModel.addWish(item.convertToWishBook()) }
                                )
                            } else {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.remove_from_wish,
                                        item.bookInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                    dismissAction = { viewModel.removeWish(item.bookInfo.uuid) }
                                )
                            }
                        },
                        onAddReadingClick = { checked ->
                            if (checked) {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.added_into_reading,
                                        item.bookInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                    dismissAction = { viewModel.addReading(item.convertToReadingBook()) }
                                )
                            } else {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.remove_from_reading,
                                        item.bookInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                    dismissAction = { viewModel.removeReading(item.bookInfo.uuid) }
                                )
                            }
                        })
                }
                if (index == 4) {
                    GoogleAdView(adSize = AdSize.BANNER, keyword = keyword)
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

private fun showSnackbar(
    message: String,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    actionLabel: String,
    dismissAction: () -> Unit
) {
    scope.launch {
        when (snackbarHostState.showSnackbar(
            message = message,
            // TODO: tap the snackbar action button won't change the toggle button status, disable it now
//            actionLabel = actionLabel,
            duration = SnackbarDuration.Short
        )) {
            SnackbarResult.Dismissed -> dismissAction()
            SnackbarResult.ActionPerformed -> Unit
        }
    }
}

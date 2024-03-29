package app.tinks.readkeeper.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.search.ui.components.RkSearchErrorItem
import app.tinks.readkeeper.search.ui.components.RkSearchTipItem
import app.tinks.readkeeper.uicomponent.cellview.GoogleAdView
import app.tinks.readkeeper.uicomponent.list.BookListCard
import com.google.android.gms.ads.AdSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SearchResultPage(
    onTitleClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: SearchResultViewModel = viewModel()
) {
    val keyword = viewModel.keyword
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
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
                })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        val books = viewModel.flow.collectAsLazyPagingItems()
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when {
                books.loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp)
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
            itemsIndexed(items = books, key = { _, item -> item.basicInfo.uuid }) { index, item ->
                if (item != null) {
                    BookListCard(
                        book = item,
                        modifier = Modifier.fillMaxWidth(),
                        onWishButtonClick = { checked ->
                            if (checked) {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.added_into_wish,
                                        item.basicInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                )
                                viewModel.add(item.update(status = Status.WISH))
                            } else {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.remove_from_wish,
                                        item.basicInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                )
                                viewModel.remove(item.basicInfo.uuid)
                            }
                        },
                        onReadingButtonClick = { checked ->
                            if (checked) {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.added_into_reading,
                                        item.basicInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                )
                                viewModel.add(item.update(status = Status.READING))
                            } else {
                                showSnackbar(
                                    message = context.getString(
                                        R.string.remove_from_reading,
                                        item.basicInfo.title
                                    ),
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    actionLabel = context.getString(R.string.undo),
                                )
                                viewModel.remove(item.basicInfo.uuid)
                            }
                        }
                    )
                }
                if (index == 4) {
                    GoogleAdView(adSize = AdSize.MEDIUM_RECTANGLE, keyword = keyword)
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
    dismissAction: () -> Unit = {}
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

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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.github.tinkzhang.readkeeper.search.SearchBookItem
import com.github.tinkzhang.readkeeper.search.SearchViewModel

@Composable
fun SearchResultScreen(viewModel: SearchViewModel, keyword: String?) {
    viewModel.searchKeyword.value = keyword
//    val keyword by viewModel.searchKeyword.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(keyword.toString()) },
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
            val books = viewModel.flow.collectAsLazyPagingItems()

            LazyColumn {
                if (books.loadState.refresh == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
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
//            if (isLoading == true) {
//                Box(
//                    modifier = Modifier.fillMaxSize(0.2f),
//                    contentAlignment = Alignment.Center,
//                ) {
//                    CircularProgressIndicator()
//                }
//            } else {
//                Column {
//                    books?.let {
//                        LazyColumn {
//                            itemsIndexed(books!!) { _, book ->
//                                SearchBookItem(book = book)
//                            }
//                        }
//                    } ?: Text(text = "Empty List")
//                }
//            }
//        Column {
//            Text(if (isLoading == true) {
//                "Loading"
//            } else {
//                keyword
//            },
//                modifier = Modifier.clickable {
//                    viewModel.searchBook(keyword)
//                }
//            )
//        }
        }
    }
}

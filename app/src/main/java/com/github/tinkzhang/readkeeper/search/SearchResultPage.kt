package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.tinkzhang.readkeeper.search.SearchBookItem
import com.github.tinkzhang.readkeeper.search.SearchViewModel

@Composable
fun SearchResultPage(viewModel: SearchViewModel) {
    val isLoading by viewModel.isLoading.observeAsState()
    val books by viewModel.books.observeAsState()
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize(),
    ) {
        if (isLoading == true) {
            Box(
                modifier = Modifier.fillMaxSize(0.2f),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column {
                books?.let {
                    LazyColumn {
                        itemsIndexed(books!!) { index, book ->
                            SearchBookItem(book = book)
                        }
                    }
                } ?: Text(text = "Empty List")
            }
        }
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

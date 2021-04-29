package com.github.tinkzhang.readkeeper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.tinkzhang.readkeeper.search.SearchBook
import com.github.tinkzhang.readkeeper.search.SearchBookItem
import com.github.tinkzhang.readkeeper.search.SearchViewModel
import com.github.tinkzhang.readkeeper.search.buildSample

@Composable
fun SearchResultPage(keyword: String) {
    val viewModel: SearchViewModel = viewModel()
    val isLoading by viewModel.isLoading.observeAsState()
    val books by viewModel.books.observeAsState()
    Column {
        Text(if (isLoading == true) {
            "Loading"
        } else {
            keyword
        },
            modifier = Modifier.clickable {
                viewModel.searchBook(keyword)
            }
        )
        books?.let {
            LazyColumn {
                itemsIndexed(books!!) { index, book ->
                    SearchBookItem(book = book)
                }
            }
        } ?: Text(text = "Empty List")
    }
}

package com.github.tinkzhang.readkeeper.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.reading.ReadingBook
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.ui.components.RkCategoryChip
import timber.log.Timber

@Composable
fun ReadingListScreen(viewModel: ReadingViewModel) {
    val selectedCategory = viewModel.selectedCategory.value
    val books: MutableList<ReadingBook> by viewModel.list.observeAsState(mutableListOf())

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
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
        LazyColumn(modifier = Modifier.fillMaxWidth(  )) {
            Timber.d("${books.size}")
            items(books) {
                Text(it.title)
            }
        }
    }
}

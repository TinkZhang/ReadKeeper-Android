package com.github.tinkzhang.readkeeper.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.reading.ReadingViewModel
import com.github.tinkzhang.readkeeper.ui.components.RkCategoryChip

@Composable
fun ReadingListScreen(viewModel: ReadingViewModel) {
    val selectedCategory = viewModel.selectedCategory.value

    Column {
//        val scrollState = rememberLazyListState()
        val composableScope = rememberCoroutineScope()
        LazyRow(
            modifier = Modifier
                .padding(8.dp),
//            state = scrollState,
        ) {
            val categories = viewModel.categories.value
            if (categories != null) {
                itemsIndexed(categories) { _, category ->
                    RkCategoryChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            viewModel.onSelectedCategoryChanged(category)
//                            viewModel.onChangeCategoryScrollPosition(scrollState)
                        },
                        onExecuteSearch = viewModel::newSearch,
                    )
                }
            }
        }
    }
}

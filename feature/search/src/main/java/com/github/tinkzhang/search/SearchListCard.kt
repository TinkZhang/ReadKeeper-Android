package com.github.tinkzhang.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.basic.model.SearchBook
import com.github.tinkzhang.basic.model.SearchBookFactory
import com.github.tinkzhang.search.ui.components.SearchCardBottom
import com.github.tinkzhang.search.ui.components.SearchCardMetadata
import com.github.tinkzhang.uicomponent.BookCardImageSmall
import com.github.tinkzhang.uicomponent.BookListCard

@ExperimentalMaterial3Api
@Composable
fun SearchListCard(
    book: SearchBook,
    onAddWishClick: (Boolean) -> Unit = {},
    onAddReadingClick: (Boolean) -> Unit = {},
) {
    BookListCard(
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = { SearchCardMetadata(book = book) },
        bottom = {
            SearchCardBottom(
                onWishButtonClicked = onAddWishClick,
                onReadingButtonClicked = onAddReadingClick
            )
        },
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun SearchListCard() {
    SearchListCard(book = SearchBookFactory.buildSample())
}

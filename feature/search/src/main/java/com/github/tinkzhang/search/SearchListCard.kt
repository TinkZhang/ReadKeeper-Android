package com.github.tinkzhang.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.basic.model.SearchBook
import com.github.tinkzhang.basic.model.SearchBookFactory
import com.github.tinkzhang.search.ui.components.SearchCardBottom
import com.github.tinkzhang.search.ui.components.SearchCardMetadata
import com.github.tinkzhang.uicomponent.BookCardImageSmall
import com.github.tinkzhang.uicomponent.BookListCard

@Composable
fun SearchListCard(book: SearchBook, searchResultViewModel: SearchResultViewModel? = null) {
    BookListCard(
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = { SearchCardMetadata(book = book) },
        bottom = {
            SearchCardBottom(
                onWishButtonClicked = { checked ->
                    if (checked) {
                        searchResultViewModel?.addWish(book.convertToWishBook())
                    } else {
                        searchResultViewModel?.removeWish(book.bookInfo.uuid)
                    }
                },
                onReadingButtonClicked = { checked ->
                    if (checked) {
                        searchResultViewModel?.addReading(book.convertToReadingBook())
                    } else {
                        searchResultViewModel?.removeReading(book.bookInfo.uuid)
                    }
                }
            )
        },
    )
}

@Preview
@Composable
private fun SearchListCard() {
    SearchListCard(book = SearchBookFactory.buildSample())
}

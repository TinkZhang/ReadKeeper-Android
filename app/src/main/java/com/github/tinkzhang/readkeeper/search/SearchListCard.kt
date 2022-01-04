package com.github.tinkzhang.readkeeper.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.readkeeper.model.SearchBook
import com.github.tinkzhang.readkeeper.model.SearchBookFactory
import com.github.tinkzhang.uicomponent.BookListCard
import com.github.tinkzhang.readkeeper.search.components.SearchCardBottom
import com.github.tinkzhang.readkeeper.search.components.SearchCardMetadata
import com.github.tinkzhang.uicomponent.BookCardImage

@Composable
fun SearchListCard(book: SearchBook, searchResultViewModel: SearchResultViewModel? = null) {
    BookListCard(
        left = { BookCardImage(book = book) },
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

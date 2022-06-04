package com.github.tinkzhang.search

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.tinkzhang.basic.model.SearchBook
import com.github.tinkzhang.basic.model.SearchBookFactory
import com.github.tinkzhang.search.ui.components.SearchCardMetadata
import com.github.tinkzhang.uicomponent.BookCardImageSmall
import com.github.tinkzhang.uicomponent.BookListItem
import com.github.tinkzhang.uicomponent.PreviewAnnotation
import com.github.tinkzhang.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchListItem(
    book: SearchBook,
    modifier: Modifier = Modifier,
    onAddWishClick: (Boolean) -> Unit = {},
    onAddReadingClick: (Boolean) -> Unit = {},
) {
    BookListItem(
        modifier = modifier,
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = {
            SearchCardMetadata(
                book = book,
                onReadingButtonClicked = onAddReadingClick,
                onWishButtonClicked = onAddWishClick
            )
        },
    )
}

@PreviewAnnotation
@Composable
private fun SearchListItemPreview() {
    ReadKeeperTheme {
        Surface {
            SearchListItem(book = SearchBookFactory.buildSample())
        }
    }
}

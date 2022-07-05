package app.github.tinkzhang.search

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.github.tinkzhang.search.ui.components.SearchCardMetadata
import app.github.tinkzhang.uicomponent.BookCardImageSmall
import app.github.tinkzhang.uicomponent.BookListItem
import app.github.tinkzhang.uicomponent.PreviewAnnotation
import app.github.tinkzhang.uicomponent.theme.ReadKeeperTheme
import app.tinks.readkeeper.basic.model.SearchBook
import app.tinks.readkeeper.basic.model.SearchBookFactory

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

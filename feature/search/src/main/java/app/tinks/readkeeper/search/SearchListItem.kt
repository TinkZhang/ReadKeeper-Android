package app.tinks.readkeeper.search

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.search.ui.components.SearchCardMetadata
import app.tinks.readkeeper.uicomponent.BookCardImageSmall
import app.tinks.readkeeper.uicomponent.BookListItem
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchListItem(
    book: Book,
    modifier: Modifier = Modifier,
    onAddWishClick: (Boolean) -> Unit = {},
    onAddReadingClick: (Boolean) -> Unit = {},
) {
    BookListItem(
        modifier = modifier,
        left = { BookCardImageSmall(url = book.basicInfo.imageUrl, title = book.basicInfo.title) },
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
            SearchListItem(book = BookFactory.buildSearchSample())
        }
    }
}

package app.tinks.readkeeper.uicomponent.list

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.list.bookinfo.ArchivedBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.ReadingBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.SearchBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.WishBookListInfo
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun ListBookInfo(
    book: Book, modifier: Modifier = Modifier,
    onAddProgressClick: () -> Unit = {},
    onSetPlatformClick: () -> Unit = {},
    onWishButtonClick: (Boolean) -> Unit = {},
    onReadingButtonClick: (Boolean) -> Unit = {},
    onMoveFromWishToReadingClick: (Book) -> Unit = {},
) {
    when (book.status) {
        Status.READING -> ReadingBookListInfo(
            book = book,
            modifier = modifier,
            onAddProgressClick = onAddProgressClick,
            onSetPlatformClick = onSetPlatformClick,
        )

        Status.WISH -> WishBookListInfo(
            book = book,
            modifier = modifier,
            onMoveFromWishToReadingClick = onMoveFromWishToReadingClick
        )

        Status.ARCHIVED -> ArchivedBookListInfo(book, modifier)
        Status.SEARCH -> SearchBookListInfo(
            book = book,
            modifier = modifier,
            onWishButtonClick = onWishButtonClick,
            onReadingButtonClick = onReadingButtonClick
        )
    }
}

@PreviewAnnotation
@Composable
private fun SearchBookInfoPreview() {
    ReadKeeperTheme {
        Surface {
            ListBookInfo(book = BookFactory.buildSearchSample())
        }
    }
}

@PreviewAnnotation
@Composable
private fun ReadingBookInfoPreview() {
    ReadKeeperTheme {
        Surface {
            ListBookInfo(book = BookFactory.buildReadingSample())
        }
    }
}

@PreviewAnnotation
@Composable
private fun WishBookInfoPreview() {
    ReadKeeperTheme {
        Surface {
            ListBookInfo(book = BookFactory.buildWishSample())
        }
    }
}


@PreviewAnnotation
@Composable
private fun ArchivedBookInfoPreview() {
    ReadKeeperTheme {
        Surface {
            ListBookInfo(book = BookFactory.buildArchivedSample())
        }
    }
}

package app.tinks.readkeeper.uicomponent.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.uicomponent.list.bookinfo.ArchivedBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.ReadingBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.SearchBookListInfo
import app.tinks.readkeeper.uicomponent.list.bookinfo.WishBookListInfo

@Composable
fun ListBookInfo(
    book: Book, modifier: Modifier = Modifier,
    onAddProgressClicked: () -> Unit = {},
    onWishButtonClicked: (Boolean) -> Unit = {},
    onReadingButtonClicked: (Boolean) -> Unit = {}
) {
    when (book.status) {
        Status.READING -> ReadingBookListInfo(
            book = book,
            modifier = modifier,
            onAddProgressClicked = onAddProgressClicked
        )
        Status.WISH -> WishBookListInfo(
            book = book,
            modifier = modifier,
            onReadingButtonClicked = onReadingButtonClicked
        )
        Status.ARCHIVED -> ArchivedBookListInfo(book, modifier)
        Status.SEARCH -> SearchBookListInfo(
            book = book,
            modifier = modifier,
            onWishButtonClicked = onWishButtonClicked,
            onReadingButtonClicked = onReadingButtonClicked
        )
    }
}

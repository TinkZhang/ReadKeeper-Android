package app.tinks.readkeeper.uicomponent.list.bookinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.InfoText
import app.tinks.readkeeper.uicomponent.cellview.PageText
import app.tinks.readkeeper.uicomponent.cellview.RatingText
import app.tinks.readkeeper.uicomponent.cellview.ReadingIconToggleButton
import app.tinks.readkeeper.uicomponent.cellview.TimeText
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun WishBookListInfo(
    book: Book,
    modifier: Modifier = Modifier,
    onMoveFromWishToReadingClick: (Book) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        InfoText(text = book.basicInfo.author, maxLine = 2)
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            RatingText(rating = book.basicInfo.rating)
            PageText(pageFormat = book.pageFormat, realPage = book.realPages)
            TimeText(book.timeInfo.editedTime)
        }
        ReadingIconToggleButton(
            checked = false,
            modifier = Modifier
                .align(Alignment.End)
        ) {
            onMoveFromWishToReadingClick(book)
        }
    }
}

@PreviewAnnotation
@Composable
private fun WishBook() {
    ReadKeeperTheme {
        Surface {
            WishBookListInfo(book = BookFactory.buildWishSample())
        }
    }
}

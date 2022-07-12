package app.tinks.readkeeper.uicomponent.list.bookinfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressCircle
import app.tinks.readkeeper.uicomponent.cellview.PageText
import app.tinks.readkeeper.uicomponent.cellview.RatingText
import app.tinks.readkeeper.uicomponent.cellview.TimeText
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme


@Composable
fun ArchivedBookListInfo(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            RatingText(rating = book.basicInfo.rating)
            PageText(pageFormat = book.pageFormat, realPage = book.realPages)
            TimeText(book.timeInfo.editedTime)
        }
        if (book.progress == book.realPages && book.progress != 0) {
            Icon(
                Icons.Outlined.CheckCircle,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 32.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
        } else if (book.progress != 0) {
            ReadingProgressCircle(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 32.dp)
                    .size(24.dp)
                    .padding(4.dp),
                progress = book.progress.toFloat() / book.realPages
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun ArchivedFinishedBook() {
    ReadKeeperTheme {
        Surface {
            ArchivedBookListInfo(
                book = BookFactory.buildArchivedSample().copy(progress = 123, realPages = 123)
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun ArchivedUnfinishedBook() {
    ReadKeeperTheme {
        Surface {
            ArchivedBookListInfo(
                book = BookFactory.buildArchivedSample().copy(progress = 123, realPages = 345)
            )
        }
    }
}

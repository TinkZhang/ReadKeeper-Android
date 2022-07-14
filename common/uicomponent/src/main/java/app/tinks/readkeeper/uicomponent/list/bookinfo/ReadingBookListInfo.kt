package app.tinks.readkeeper.uicomponent.list.bookinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressBar
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressText
import app.tinks.readkeeper.uicomponent.cellview.TimeText
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme


@Composable
fun ReadingBookListInfo(
    book: Book,
    modifier: Modifier = Modifier,
    onAddProgressClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeText(book.timeInfo.addedTime)
            book.platform?.icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = book.platform?.label
                )
            }
        }
        if (book.progress != 0) {
            ReadingProgressText(
                format = book.pageFormat,
                position = book.progress,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReadingProgressBar(
                    modifier = Modifier
                        .weight(1.0f)
                        .height(24.dp),
                    progress = book.progress.toFloat() / book.realPages
                )
                IconButton(onClick = onAddProgressClicked) {
                    Icon(Icons.Default.DataSaverOn, contentDescription = "Localized description")
                }
            }

        } else {
            TextButton(
                onClick = onAddProgressClicked,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.start_reading),
                )
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun ReadingNewBook() {
    ReadKeeperTheme {
        Surface {
            ReadingBookListInfo(book = BookFactory.buildReadingSample())
        }
    }
}

@PreviewAnnotation
@Composable
private fun ReadingInProgressBook() {
    ReadKeeperTheme {
        Surface {
            ReadingBookListInfo(
                book = BookFactory
                    .buildReadingSample()
                    .copy(progress = 123, realPages = 455)
            )
        }
    }
}
package app.tinks.readkeeper.uicomponent.list.bookinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingBookListInfo(
    book: Book,
    modifier: Modifier = Modifier,
    onAddProgressClicked: () -> Unit = {},
    onEditBookClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            book.platform?.let { platform ->
                AssistChip(
                    onClick = { },
                    label = { Text(platform.label) },
                    leadingIcon = {
                        Image(
                            painterResource(id = platform.icon),
                            null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )
            } ?: Box(Modifier)
            TimeText(book.timeInfo.editedTime, isLongFormat = false)
        }
        if (book.platform != null) {
            ReadingProgressText(
                format = book.pageFormat,
                position = book.progress,
                style = MaterialTheme.typography.bodyMedium,
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
                onClick = onEditBookClicked,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.set_platform),
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
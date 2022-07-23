package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.*
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun InfoSection(book: Book) {
    Row(modifier = Modifier.fillMaxWidth()) {
        BookCardImageLarge(url = book.basicInfo.imageUrl, title = book.basicInfo.title)
        InfoAttributes(book = book, modifier = Modifier.weight(2.0f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAttributes(book: Book, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InfoText(text = book.basicInfo.author, maxLine = 2)
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            RatingText(rating = book.basicInfo.rating)
            PubYearText(year = book.basicInfo.pubYear)
            PageText(pageFormat = book.pageFormat, realPage = book.realPages)
        }
        IsbnText(isbn = book.basicInfo.isbn)
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
        }
        TimeText(time = book.timeInfo.addedTime, isLongFormat = true)
    }
}

@PreviewAnnotation
@Composable
private fun DetailInfoSection() {
    ReadKeeperTheme {
        Surface {
            InfoSection(book = BookFactory.buildReadingSample())
        }
    }
}

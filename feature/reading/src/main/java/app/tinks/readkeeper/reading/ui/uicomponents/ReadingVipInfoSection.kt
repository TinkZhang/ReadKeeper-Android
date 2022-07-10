package app.tinks.readkeeper.reading.ui.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.BasicInfo
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.BookCardImageLarge

@Composable
fun ReadingVipInfoSection(book: Book) {
    Row(modifier = Modifier.fillMaxWidth()) {
        BookCardImageLarge(url = book.basicInfo.imageUrl, title = book.basicInfo.title)
        ReadingVipMetadata(book = book.basicInfo, modifier = Modifier.weight(2.0f))
    }
}

@Composable
fun ReadingVipMetadata(book: BasicInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (book.author.isNotEmpty()) {
            Text(
                "✍️  " + book.author,
                style = if (book.author.length > 40) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
                maxLines = 3,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (book.rating != 0.0) {
            Text(
                text = "⭐  Rating: ${book.rating}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (book.pubYear != 0) {
            Text(
                "\uD83D\uDDD3️  Published in ${book.pubYear}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookVipInfoSectionPreview() {
    ReadingVipInfoSection(book = BookFactory.buildSearchSample())
}
package com.github.readkeeper.archived.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.model.ArchivedBook
import com.github.tinkzhang.basic.model.ArchivedBookFactory
import com.github.tinkzhang.uicomponent.BookCardImageLarge
import java.text.DateFormat

@Composable
fun ArchivedVipInfoSection(book: ArchivedBook) {
    Row(modifier = Modifier.fillMaxWidth()) {
        BookCardImageLarge(url = book.bookInfo.imageUrl, title = book.bookInfo.title)
        ArchivedVipMetadata(book = book, modifier = Modifier.weight(2.0f))
    }
}

@Composable
fun ArchivedVipMetadata(book: ArchivedBook, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (book.bookInfo.author.isNotEmpty()) {
            Text(
                "✍️  " + book.bookInfo.author,
                style = if (book.bookInfo.author.length > 40) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
                maxLines = 3,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (book.bookInfo.rating != 0.0) {
            Text(
                text = "⭐  Rating: ${book.bookInfo.rating}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (book.bookInfo.pubYear != 0) {
            Text(
                "\uD83D\uDDD3️  Published in ${book.bookInfo.pubYear}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            "Archived on ${
                DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .format(book.timeInfo.addedTime.toDate())
            }",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookVipInfoSectionPreview() {
    ArchivedVipInfoSection(book = ArchivedBookFactory.buildSample())
}
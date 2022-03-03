package com.github.tinkzhang.wish.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.github.tinkzhang.basic.model.WishBook
import com.github.tinkzhang.basic.model.WishBookFactory
import com.github.tinkzhang.uicomponent.BookCardImageLarge
import java.text.DateFormat

@Composable
fun WishVipInfoSection(book: WishBook) {
    Row(modifier = Modifier.fillMaxWidth()) {
        BookCardImageLarge(url = book.bookInfo.imageUrl, title = book.bookInfo.title)
        WishVipMetadata(book = book, modifier = Modifier.weight(2.0f))
    }
}

@Composable
fun WishVipMetadata(book: WishBook, modifier: Modifier = Modifier) {
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
            "Added on ${
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
    WishVipInfoSection(book = WishBookFactory.buildSample())
}
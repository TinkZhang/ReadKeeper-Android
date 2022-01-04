package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.model.ReadingBook
import com.github.tinkzhang.readkeeper.model.ReadingBookFactory
import com.github.tinkzhang.readkeeper.model.BookInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ReadingVipInfoSection(book: ReadingBook) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.weight(1.0f)) {
            Image(
                painter = rememberImagePainter(
                    data = book.bookInfo.imageUrl,
                    builder = {
                        placeholder(R.drawable.book_sample)
                    }),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
        ReadingVipMetadata(book = book.bookInfo, modifier = Modifier.weight(2.0f))
    }
}

@Composable
fun ReadingVipMetadata(book: BookInfo, modifier: Modifier = Modifier) {
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
    ReadingVipInfoSection(book = ReadingBookFactory.buildSample())
}
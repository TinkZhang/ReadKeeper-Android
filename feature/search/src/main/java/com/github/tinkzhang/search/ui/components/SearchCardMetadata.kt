package com.github.tinkzhang.search.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.model.SearchBook
import com.github.tinkzhang.basic.model.SearchBookFactory
import com.github.tinkzhang.uicomponent.PreviewAnnotation
import com.github.tinkzhang.uicomponent.ReadingIconToggleButton
import com.github.tinkzhang.uicomponent.WishIconToggleButton
import com.github.tinkzhang.uicomponent.theme.ReadKeeperTheme


@Composable
fun SearchCardMetadata(
    book: SearchBook,
    modifier: Modifier = Modifier,
    onReadingButtonClicked: (Boolean) -> Unit = {},
    onWishButtonClicked: (Boolean) -> Unit = {},
) {
    var isInReading: Boolean by remember { mutableStateOf(false) }
    var isInWish: Boolean by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 0.dp, bottom = 0.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                book.bookInfo.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "✍️   " + book.bookInfo.author,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "\uD83D\uDCC5   " + book.bookInfo.pubYear.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Row() {
                ReadingIconToggleButton(checked = isInReading) {
                    onReadingButtonClicked(it)
                    isInReading = !isInReading
                }
                Spacer(modifier = Modifier.width(16.dp))
                WishIconToggleButton(checked = isInWish) {
                    onWishButtonClicked(it)
                    isInWish = !isInWish
                }
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun SearchCardMetadataPreview() {
    ReadKeeperTheme {
        Surface {
            SearchCardMetadata(book = SearchBookFactory.buildSample())
        }
    }
}

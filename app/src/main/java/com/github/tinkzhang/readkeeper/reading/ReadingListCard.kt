package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.common.data.ReadingBookFactory
import com.github.tinkzhang.readkeeper.reading.uicomponents.BookCardImage
import com.github.tinkzhang.readkeeper.reading.uicomponents.ReadingCardEditBottom
import com.github.tinkzhang.readkeeper.reading.uicomponents.ReadingCardMetadata
import com.github.tinkzhang.readkeeper.reading.uicomponents.ReadingCardProgressBottom

@Composable
fun ReadingListCard(book: ReadingBook, navController: NavController? = null) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { navController?.navigate("reading_item/${book.uuid}?open_progress_dialog=${false}") },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BookCardImage(book = book)
                    ReadingCardMetadata(book = book)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Divider()
                Spacer(modifier = Modifier.width(8.dp))
                if (book.formatEdited) {
                    ReadingCardProgressBottom(book = book) {
                        navController?.navigate("reading_item/${book.uuid}?open_progress_dialog=${true}")
                    }
                } else {
                    ReadingCardEditBottom() {
                        navController?.navigate("reading_item/${book.uuid}?open_edit_dialog=${true}")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReadingCard() {
    ReadingListCard(book = ReadingBookFactory.buildSample())
}

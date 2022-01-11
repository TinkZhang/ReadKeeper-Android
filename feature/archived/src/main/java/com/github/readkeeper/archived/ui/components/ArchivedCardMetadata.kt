package com.github.readkeeper.archived.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.model.ArchivedBook
import com.github.tinkzhang.basic.model.ArchivedBookFactory

@Composable
fun ArchivedCardMetadata(
    book: ArchivedBook,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            book.bookInfo.title,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 3,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Column(verticalArrangement = Arrangement.Bottom) {
            Text(
                "✍️   " + book.bookInfo.author,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 8.dp),
            )
//            RkCategoryChip(category = book.category ?: "", isSelected = true)
        }
    }
}

@Preview
@Composable
private fun ArchivedCardMetadataPreview() {
    ArchivedCardMetadata(book = ArchivedBookFactory.buildSample())
}

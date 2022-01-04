package com.github.tinkzhang.reading.ui.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.model.ReadingBook
import com.github.tinkzhang.readkeeper.model.ReadingBookFactory
import com.github.tinkzhang.uicomponent.ReadingProgressBar
import com.github.tinkzhang.uicomponent.ReadingProgressText

@Composable
fun ReadingCardProgressBottom(
    book: ReadingBook,
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.weight(4.0f)) {
            Icon(
                painterResource(id = book.platform.icon),
                contentDescription = book.platform.label,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.fillMaxWidth()) {
                ReadingProgressText(
                    format = book.pageFormat,
                    position = book.records.lastOrNull()?.endPage ?: 0,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
                ReadingProgressBar(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                    progress = book.records.lastOrNull()?.endPage?.toFloat()
                        ?.div(book.bookInfo.pages.toFloat()) ?: 0.0f
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(onClick = onButtonClicked, Modifier.weight(1.0f)) {
            Icon(Icons.Default.DataSaverOn, contentDescription = "Add Progress")
        }
    }
}

@Preview
@Composable
private fun ReadingCardBottomPreview() {
    ReadingCardProgressBottom(book = ReadingBookFactory.buildSample())
}

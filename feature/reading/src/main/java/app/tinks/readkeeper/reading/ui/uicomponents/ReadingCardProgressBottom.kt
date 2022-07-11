package app.tinks.readkeeper.reading.ui.uicomponents

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
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.ReadingProgressBar
import app.tinks.readkeeper.uicomponent.ReadingProgressText

@Composable
fun ReadingCardProgressBottom(
    book: Book,
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
            book.platform?.let {
                Icon(
                    painterResource(id = it.icon),
                    contentDescription = it.label,
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.fillMaxWidth()) {
                ReadingProgressText(
                    format = book.pageFormat,
                    position = book.progress,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
                ReadingProgressBar(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                    progress = book.progress.toFloat()
                        .div(book.basicInfo.pages.toFloat())
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
    ReadingCardProgressBottom(book = BookFactory.buildReadingSample())
}

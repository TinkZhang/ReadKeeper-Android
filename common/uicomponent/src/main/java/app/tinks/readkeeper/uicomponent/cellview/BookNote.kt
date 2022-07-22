package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Record

@Composable
fun BookNote(
    record: Record,
    pageFormat: PageFormat,
    pages: Int,
    modifier: Modifier = Modifier,
    bigSize: Boolean = false
) {
    if (record.note.isNullOrEmpty()) return
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        ReadingProgressCircleWithText(
            format = pageFormat, position = record.endPage, totalPages = pages
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                record.note!!, style = if (bigSize) MaterialTheme.typography.bodyLarge
                else MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "--- ${record.timestamp.toDate().toLocaleString()}",
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End),
                style = if (bigSize) MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun BookNotePreview() {
    BookNote(
        record = Record(
            note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            endPage = 123,
        ), pageFormat = PageFormat.PAGE, pages = 213
    )
}

@Preview
@Composable
private fun BookNotePreview2() {
    BookNote(
        record = Record(
            note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            endPage = 123,
        ), pageFormat = PageFormat.PERCENT_1000, pages = 1000
    )
}
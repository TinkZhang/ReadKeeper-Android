package com.github.tinkzhang.reading.ui.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.model.PageFormat
import com.github.tinkzhang.basic.model.ReadingRecord
import com.github.tinkzhang.uicomponent.Section
import com.google.firebase.Timestamp

@Composable
fun ReadingVipNoteSection(records: List<ReadingRecord>, pageFormat: PageFormat, pages: Int) {
    Section(title = "Reading Notes") {
        when {
            records.isEmpty() -> {
                Text(
                    "\uD83D\uDDF3️  Empty reading note.",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            records.size < 4 -> {
                records.forEach {
                    BookNote(it, pageFormat, pages)
                }
            }
            else -> {
                records.subList(0, 4).forEach {
                    BookNote(it, pageFormat, pages)
                }
            }
        }
        if (records.isNotEmpty()) {
            OutlinedButton(
                onClick = {},
                Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text("Show All Notes")
            }
        }
    }
}

@Composable
fun BookNote(record: ReadingRecord, pageFormat: PageFormat, pages: Int) {
    if (record.note.isNotBlank()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ReadingProgressCircleWithText(
                format = pageFormat,
                position = record.endPage,
                totalPages = pages
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    record.note,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "--- ${record.timestamp.toDate().toLocaleString()}",
                    Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun BookNotePreview() {
    BookNote(
        record = ReadingRecord(
            note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            endPage = 123,
            timestamp = Timestamp.now()
        ),
        pageFormat = PageFormat.PAGE,
        pages = 213
    )
}

@Preview
@Composable
private fun BookNotePreview2() {
    BookNote(
        record = ReadingRecord(
            note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            endPage = 123,
            timestamp = Timestamp.now()
        ),
        pageFormat = PageFormat.PERCENT_1000,
        pages = 1000
    )
}

@Preview
@Composable
private fun BookNoteSectionPreview() {
    ReadingVipNoteSection(
        records = listOf(
            ReadingRecord(
                note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                endPage = 123,
                timestamp = Timestamp.now()
            ), ReadingRecord(
                note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                endPage = 123,
                timestamp = Timestamp.now()
            ), ReadingRecord(
                note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                endPage = 123,
                timestamp = Timestamp.now()
            )
        ), pageFormat = PageFormat.PAGE, pages = 345
    )
}
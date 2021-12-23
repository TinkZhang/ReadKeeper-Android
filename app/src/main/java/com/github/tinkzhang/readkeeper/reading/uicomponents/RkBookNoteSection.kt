package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.common.data.PageFormat
import com.github.tinkzhang.readkeeper.common.data.ReadingNote
import com.google.firebase.Timestamp

@Composable
fun RkBookNoteSection(notes: List<ReadingNote>, pageFormat: PageFormat, pages: Int) {
    Surface(tonalElevation = 4.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Reading Notes",
                style = MaterialTheme.typography.titleMedium
            )
            Divider(Modifier.padding(vertical = 4.dp), thickness = 2.dp)
            when {
                notes.isEmpty() -> {
                    Text(
                        "\uD83D\uDDF3️  Empty reading note.",
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                notes.size < 4 -> {
                    notes.forEach {
                        RkBookNote(it, pageFormat, pages)
                        Divider()
                    }
                }
                else -> {
                    notes.subList(0, 4).forEach {
                        RkBookNote(it, pageFormat, pages)
                        Divider()
                    }
                }
            }
            if (notes.isNotEmpty()) {
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

}

@Composable
fun RkBookNote(note: ReadingNote, pageFormat: PageFormat, pages: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        RkProgress(format = pageFormat, position = note.position, totalPages = pages)
        Column(Modifier.padding(start = 8.dp)) {
            Text(
                note.note,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "--- ${note.timestamp.toDate().toLocaleString()}",
                Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun RkBookNotePreview() {
    RkBookNote(
        note = ReadingNote(
            "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            position = 123,
            timestamp = Timestamp.now()
        ),
        pageFormat = PageFormat.PAGE,
        pages = 213
    )
}

@Preview
@Composable
private fun RkBookNotePreview2() {
    RkBookNote(
        note = ReadingNote(
            "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
            position = 123,
            timestamp = Timestamp.now()
        ),
        pageFormat = PageFormat.PERCENTAGE,
        pages = 1000
    )
}

@Preview
@Composable
fun RkBookNoteSectionPreview() {
    RkBookNoteSection(
        notes = listOf(
            ReadingNote(
                "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                position = 123,
                timestamp = Timestamp.now()
            ), ReadingNote(
                "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                position = 123,
                timestamp = Timestamp.now()
            ), ReadingNote(
                "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                position = 123,
                timestamp = Timestamp.now()
            )
        ), pageFormat = PageFormat.PAGE, pages = 345
    )
}
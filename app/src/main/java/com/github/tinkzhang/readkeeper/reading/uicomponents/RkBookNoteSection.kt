package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.common.data.PageFormat
import com.github.tinkzhang.readkeeper.common.data.ReadingNote
import com.google.firebase.Timestamp

@Composable
fun RkBookNoteSection(notes: List<ReadingNote>, pageFormat: PageFormat, pages: Int) {
    Column(Modifier.fillMaxWidth()) {
        if (notes.size < 4) {
            notes.forEach {
                RkBookNote(it, pageFormat, pages)
            }
        } else {
            notes.subList(0, 4).forEach {
                RkBookNote(it, pageFormat, pages)
                Divider()
            }
        }
        Button(onClick = {}) {
            Text("Show All Notes")
        }
    }

}

@Composable
fun RkBookNote(note: ReadingNote, pageFormat: PageFormat, pages: Int) {
    Row(Modifier.fillMaxWidth()) {
        RkProgress(format = pageFormat, position = note.position, totalPages = pages)
        Column() {
            Text(
                note.note,
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = note.timestamp.toDate().toLocaleString(),
                Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
fun RkBookNotePreview() {
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
fun RkBookNotePreview2() {
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
package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.tinkzhang.readkeeper.common.data.ReadingNote

@Composable
fun RkBookNoteSection(notes: List<ReadingNote>) {
    Text("Book Notes")
}
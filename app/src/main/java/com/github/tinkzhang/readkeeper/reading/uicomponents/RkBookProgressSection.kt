package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.tinkzhang.readkeeper.common.data.PageFormat
import com.github.tinkzhang.readkeeper.common.data.ReadingPlatform
import com.github.tinkzhang.readkeeper.common.data.ReadingRecord

@Composable
fun RkBookProgressSection(
    records: List<ReadingRecord>,
    pageFormat: PageFormat,
    platform: ReadingPlatform
) {
    Text("Reading Progress")
}
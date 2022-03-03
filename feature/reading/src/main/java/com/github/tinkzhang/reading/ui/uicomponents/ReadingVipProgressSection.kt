package com.github.tinkzhang.reading.ui.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.basic.model.PageFormat
import com.github.tinkzhang.basic.model.ReadingPlatform
import com.github.tinkzhang.basic.model.ReadingRecord
import com.github.tinkzhang.uicomponent.ReadingProgressText
import com.github.tinkzhang.uicomponent.Section

@ExperimentalMaterial3Api
@Composable
fun ReadingVipProgressSection(
    lastRecord: ReadingRecord?,
    pageFormat: PageFormat,
    totalPages: Int,
    platform: ReadingPlatform
) {
    Section(title = "Reading Progress") {
        Box(
            Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = platform.icon),
                    contentDescription = platform.label
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReadingProgressText(
                    format = pageFormat,
                    position = lastRecord?.endPage ?: 0,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
            }

            ReadingProgressCircle(
                Modifier.size(192.dp),
                progress = lastRecord?.endPage?.toFloat()?.div(totalPages.toFloat()) ?: 0.0f,
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ReadingVipProgressSectionPreview() {
    ReadingVipProgressSection(
        ReadingRecord(12, 34),
        pageFormat = PageFormat.PAGE,
        platform = ReadingPlatform.KINDLE,
        totalPages = 123
    )
}

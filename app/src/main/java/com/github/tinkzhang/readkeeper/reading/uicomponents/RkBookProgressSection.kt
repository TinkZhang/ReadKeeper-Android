package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.common.data.PageFormat
import com.github.tinkzhang.readkeeper.common.data.ReadingPlatform
import com.github.tinkzhang.readkeeper.common.data.ReadingRecord

@Composable
fun RkBookProgressSection(
    lastRecord: ReadingRecord?,
    pageFormat: PageFormat,
    totalPages: Int,
    platform: ReadingPlatform
) {
    Surface(tonalElevation = 4.dp) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Reading Progress",
                style = MaterialTheme.typography.titleMedium
            )
            Divider(Modifier.padding(vertical = 4.dp), thickness = 2.dp)
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
                    RkProgressText(
                        format = pageFormat,
                        position = lastRecord?.endPage ?: 0,
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }

                RkProgressCircle(
                    Modifier.size(192.dp),
                    progress = lastRecord?.endPage?.toFloat()?.div(totalPages.toFloat()) ?: 0.0f,
                )
            }
        }
    }


}

@Preview
@Composable
fun RkBookProgressSectionPreview() {
    RkBookProgressSection(
        ReadingRecord(12, 34),
        pageFormat = PageFormat.PAGE,
        platform = ReadingPlatform.KINDLE,
        totalPages = 123
    )
}

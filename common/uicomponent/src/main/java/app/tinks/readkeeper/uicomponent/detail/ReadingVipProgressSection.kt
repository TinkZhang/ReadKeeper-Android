package app.tinks.readkeeper.uicomponent.detail

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
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Platform
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.Section
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressCircle
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressText
import com.google.firebase.Timestamp

@ExperimentalMaterial3Api
@Composable
fun ReadingVipProgressSection(
    lastRecord: Record?,
    pageFormat: PageFormat,
    totalPages: Int,
    platform: Platform?
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
                platform?.let {
                    Image(
                        painter = painterResource(id = platform.icon),
                        contentDescription = platform.label
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                ReadingProgressText(
                    format = pageFormat,
                    position = lastRecord?.endPage ?: 0,
                    style = MaterialTheme.typography.titleMedium,
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
        Record(uuid = "hello", startPage = 34, endPage = 55, timestamp = Timestamp.now()),
        pageFormat = PageFormat.PAGE,
        platform = Platform.KINDLE,
        totalPages = 123
    )
}

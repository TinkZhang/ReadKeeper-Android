package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Platform
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressCircle
import app.tinks.readkeeper.uicomponent.cellview.ReadingProgressText
import app.tinks.readkeeper.uicomponent.detail.actionsection.DetailTitleSection
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme
import com.google.firebase.Timestamp

@Composable
fun ProgressSection(
    lastRecord: Record?,
    pageFormat: PageFormat,
    totalPages: Int,
    platform: Platform?,
    modifier: Modifier = Modifier
) {
    val isEmpty = platform == null || lastRecord == null
    AnimatedVisibility(visible = !isEmpty) {
        if (platform != null && lastRecord != null) {
            DetailTitleSection(
                title = stringResource(id = R.string.progress_section_title),
                modifier = modifier
            ) {
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
                            position = lastRecord.endPage,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    ReadingProgressCircle(
                        Modifier.size(192.dp),
                        progress = lastRecord.endPage.toFloat().div(totalPages.toFloat()),
                    )
                }
            }
        }
    }

}

@PreviewAnnotation
@Composable
private fun ProgressSectionPreview() {
    ReadKeeperTheme {
        Surface {
            ProgressSection(
                Record(uuid = "hello", startPage = 34, endPage = 55, timestamp = Timestamp.now()),
                pageFormat = PageFormat.PAGE,
                platform = Platform.KINDLE,
                totalPages = 123
            )
        }
    }
}

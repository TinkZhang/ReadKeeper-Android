package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.BookNote
import app.tinks.readkeeper.uicomponent.detail.actionsection.DetailTitleSection
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun NoteSection(
    records: List<Record>,
    pageFormat: PageFormat,
    realPages: Int,
    modifier: Modifier = Modifier,
    onShowAllNotesClick: () -> Unit = {},
) {
    if (records.isEmpty()) return
    DetailTitleSection(
        modifier = modifier,
        title = stringResource(id = R.string.note_section_title)
    ) {
        records.take(3).forEach {
            BookNote(it, pageFormat, realPages)
        }
        if (records.isNotEmpty()) {
            OutlinedButton(
                onClick = onShowAllNotesClick,
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.show_all_notes))
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun NoteSection() {
    ReadKeeperTheme {
        Surface {
            NoteSection(
                records = listOf(
                    Record(
                        note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                        endPage = 123,
                    ), Record(
                        note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                        endPage = 156,
                    ), Record(
                        note = "this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. this is a test note for testing only, you don't need spend much time on reading this silly hello world text. ",
                        endPage = 232,
                    )
                ), pageFormat = PageFormat.PAGE, realPages = 345
            )
        }
    }
}

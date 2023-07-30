package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.NoteEditField
import app.tinks.readkeeper.uicomponent.cellview.ProgressEditField

@Composable
fun AddProgressContent(
    book: Book,
    lastPage: Int = 0,
    onCancelClick: () -> Unit = {},
    onSaveClick: (Record) -> Unit = {},
) {
    var progressTextState by remember { mutableStateOf("") }
    var noteTextState by remember {
        mutableStateOf("")
    }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ProgressEditField(book, lastPage, progressTextState) { progressTextState = it }
            NoteEditField(noteTextState) { noteTextState = it }
            Row(Modifier.fillMaxWidth()) {
                TextButton(onClick = onCancelClick, modifier = Modifier.weight(1.0f)) {
                    Text(stringResource(id = R.string.cancel))
                }
                FilledTonalButton(
                    onClick = {
                        onSaveClick(
                            Record(
                                uuid = book.basicInfo.uuid,
                                startPage = lastPage,
                                endPage = when (book.pageFormat) {
                                    PageFormat.PAGE -> progressTextState.toInt()
                                    PageFormat.PERCENT_100 -> progressTextState.toInt()
                                    PageFormat.PERCENT_1000 -> (progressTextState.toDouble() * 10).toInt()
                                    PageFormat.PERCENT_10000 -> (progressTextState.toDouble() * 100).toInt()
                                },
                                note = noteTextState
                            )
                        )
                        progressTextState = ""
                        noteTextState = ""
                    }, modifier = Modifier.weight(2.0f)
                ) {
                    Text(stringResource(id = R.string.save))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProgressDialogPreview() {
    AddProgressContent(BookFactory.buildReadingSample())
}

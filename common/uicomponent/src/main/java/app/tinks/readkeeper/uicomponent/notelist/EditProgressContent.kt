package app.tinks.readkeeper.uicomponent.notelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.NoteEditField
import app.tinks.readkeeper.uicomponent.cellview.ProgressEditField
import app.tinks.readkeeper.uicomponent.detail.AddProgressContent

@Composable
fun EditProgressContent(
    book: Book,
    record: Record?,
    lastPage: Int = 0,
    onCancelClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
    onSaveClicked: (Record) -> Unit = {},
) {
    if (record == null) {
        Box(modifier = Modifier.size(1.dp))
        return
    }
    var noteTextState by remember {
        mutableStateOf(record.note ?: "")
    }
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ProgressEditField(book, lastPage, value = "", readOnly = true)
            NoteEditField(noteTextState) { noteTextState = it }
            Row(Modifier.fillMaxWidth()) {
                TextButton(onClick = onDeleteClicked, modifier = Modifier.weight(1.0f)) {
                    Text(
                        stringResource(id = R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                TextButton(onClick = onCancelClicked, modifier = Modifier.weight(1.0f)) {
                    Text(stringResource(id = R.string.cancel))
                }
                FilledTonalButton(
                    onClick = {
                        onSaveClicked(
                            record.copy(note = noteTextState)
                        )
                        noteTextState = ""
                    }, modifier = Modifier.weight(1.5f)
                ) {
                    Text(stringResource(id = R.string.update))
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

package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.R

@Composable
fun AddProgressContent(
    book: Book,
    lastPage: Int = 0,
    onCancelClicked: () -> Unit = {},
    onSaveClicked: (Record) -> Unit = {},
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
            ProgressEditSection(book, lastPage, progressTextState) { progressTextState = it }
            NoteEditSection(noteTextState) { noteTextState = it }
            Row(Modifier.fillMaxWidth()) {
                TextButton(onClick = onCancelClicked, modifier = Modifier.weight(1.0f)) {
                    Text(stringResource(id = R.string.cancel))
                }
                FilledTonalButton(
                    onClick = {
                        onSaveClicked(
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

@Composable
private fun ProgressEditSection(
    book: Book,
    lastPage: Int,
    value: String,
    onProgressValueChange: (String) -> Unit = {},
) {
    val progressTextState = when (book.pageFormat) {
        PageFormat.PAGE, PageFormat.PERCENT_100 -> stringResource(
            id = R.string.last_position,
            lastPage
        )
        PageFormat.PERCENT_1000 -> stringResource(
            id = R.string.last_position_float_1,
            lastPage / 10.0f
        )
        PageFormat.PERCENT_10000 -> stringResource(
            id = R.string.last_position_float_2,
            lastPage / 100.0f
        )
    }
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onProgressValueChange,
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        singleLine = true,
        placeholder = { Text(progressTextState) },
        label = { Text(text = stringResource(id = R.string.progress)) },
        trailingIcon = {
            if (book.pageFormat != PageFormat.PAGE) {
                Text("%")
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
        ),
    )
}

@Composable
private fun NoteEditSection(
    value: String,
    onValueChange: (String) -> Unit = {},
) {
    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            Modifier
                .fillMaxWidth()
                .heightIn(128.dp, 256.dp),
            placeholder = { Text("Optional") },
            label = { Text(stringResource(id = R.string.note)) },
            shape = androidx.compose.material.MaterialTheme.shapes.large,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
            ),
        )
    }
}

@Preview
@Composable
private fun ProgressDialogPreview() {
    AddProgressContent(BookFactory.buildReadingSample())
}

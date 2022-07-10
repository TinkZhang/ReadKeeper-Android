package app.tinks.readkeeper.uicomponent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.basic.model.Record

@Composable
fun AddProgressDialogContent(
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
                .padding(8.dp)
        ) {
            ProgressEditSection(book, lastPage, progressTextState) { progressTextState = it }
            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
            NoteEditSection(noteTextState) { noteTextState = it }
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = onCancelClicked) {
                    Text("Cancel")
                }
                TextButton(onClick = {
                    onSaveClicked(
                        Record(
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
                }
                ) {
                    Text("Save")
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
    val progressTextState = "Last position: ${
        when (book.pageFormat) {
            PageFormat.PAGE -> lastPage
            PageFormat.PERCENT_100 -> lastPage
            PageFormat.PERCENT_1000 -> (lastPage / 10.0f)
            PageFormat.PERCENT_10000 -> (lastPage / 100.0f)
        }
    }"
    Column(Modifier.fillMaxWidth()) {
        Text("Progress", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onProgressValueChange,
            Modifier.align(CenterHorizontally),
            textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            singleLine = true,
            label = { Text(progressTextState) },
            trailingIcon = {
                if (book.pageFormat != PageFormat.PAGE) {
                    Text("%")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
            ),
        )
    }
}

@Composable
private fun NoteEditSection(
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    Column(Modifier.fillMaxWidth()) {
        Text("Note", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            Modifier
                .fillMaxWidth()
                .heightIn(128.dp, 256.dp),
            placeholder = { Text("Optional") },
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
    AddProgressDialogContent(BookFactory.buildReadingSample())
}

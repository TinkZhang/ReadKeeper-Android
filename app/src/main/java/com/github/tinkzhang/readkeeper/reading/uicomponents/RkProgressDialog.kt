package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RkProgressDialogContent(
    onCancelClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            ProgressEditSection()
            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
            NoteEditSection()
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = onCancelClicked) {
                    Text("Cancel")
                }
                TextButton(onClick = onSaveClicked) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun ProgressEditSection() {
    var progressTextState by remember { mutableStateOf("") }
    Column(Modifier.fillMaxWidth()) {
        Text("Progress", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = progressTextState,
            onValueChange = { progressTextState = it },
            Modifier.align(CenterHorizontally),
            textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            singleLine = true,
            trailingIcon = { Text("%") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
            )
        )
    }
}

@Composable
fun NoteEditSection() {
    var noteState by remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxWidth()) {
        Text("Note", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = noteState,
            onValueChange = { noteState = it },
            Modifier
                .fillMaxWidth()
                .heightIn(128.dp, 256.dp),
            placeholder = { Text("Optional") },
            shape = androidx.compose.material.MaterialTheme.shapes.large,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
            ),
        )
    }
}

@Preview
@Composable
fun RkProgressDialogPreview() {
    RkProgressDialogContent()
}

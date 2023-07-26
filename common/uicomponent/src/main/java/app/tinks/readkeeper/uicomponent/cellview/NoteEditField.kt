package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditField(
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
            placeholder = { Text(stringResource(id = R.string.optional)) },
            label = { Text(stringResource(id = R.string.note)) },
            shape = MaterialTheme.shapes.large,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colors.onSurface,
                focusedContainerColor = MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
            )
        )
    }
}

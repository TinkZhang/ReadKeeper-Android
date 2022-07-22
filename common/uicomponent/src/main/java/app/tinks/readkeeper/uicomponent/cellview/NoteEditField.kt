package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.R

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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.04f
                ),
            ),
        )
    }
}

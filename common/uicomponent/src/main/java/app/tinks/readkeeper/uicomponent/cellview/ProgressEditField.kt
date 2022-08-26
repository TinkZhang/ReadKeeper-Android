package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.uicomponent.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressEditField(
    book: Book,
    lastPage: Int,
    value: String,
    readOnly: Boolean = false,
    onProgressValueChange: (String) -> Unit = {},
) {
    if (readOnly) {
        val progressTextState = when (book.pageFormat) {
            PageFormat.PAGE, PageFormat.PERCENT_100 -> stringResource(
                id = R.string.position,
                lastPage
            )
            PageFormat.PERCENT_1000 -> stringResource(
                id = R.string.position_float_1,
                lastPage / 10.0f
            )
            PageFormat.PERCENT_10000 -> stringResource(
                id = R.string.position_float_2,
                lastPage / 100.0f
            )
        }
        OutlinedTextField(
            value = progressTextState,
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            onValueChange = {},
            textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
            label = { Text(text = stringResource(id = R.string.progress)) },
            trailingIcon = {
                if (book.pageFormat != PageFormat.PAGE) {
                    Text("%")
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
            ),
        )
    } else {
        val progressTextState =
            when (book.pageFormat) {
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
}

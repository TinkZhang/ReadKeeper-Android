package app.tinks.readkeeper.uicomponent.editbook

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun EditTitleField(
    bookTitle: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    var title by remember { mutableStateOf(bookTitle) }
    var isTitleValid by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = title,
        onValueChange = { value ->
            if (value.isEmpty()) {
                isTitleValid = true
            } else {
                isTitleValid = false
                title = value
                onValueChange(value)
            }
        },
        modifier = modifier,
        label = { Text(text = stringResource(id = R.string.title)) },
        isError = isTitleValid
    )
}

@PreviewAnnotation
@Composable
private fun EditTitleField() {
    ReadKeeperTheme {
        Surface {
            EditTitleField(bookTitle = "Book Title")
        }
    }
}
package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun IsbnText(
    isbn: String?,
    modifier: Modifier = Modifier
) {
    if (!isbn.isNullOrEmpty()) {
        InfoText(
            text = "${stringResource(id = R.string.isbn)}  $isbn",
            modifier = modifier
        )
    }
}

@PreviewAnnotation
@Composable
private fun IsbnText() {
    ReadKeeperTheme {
        Surface {
            IsbnText(isbn = "1234567890123")
        }
    }
}

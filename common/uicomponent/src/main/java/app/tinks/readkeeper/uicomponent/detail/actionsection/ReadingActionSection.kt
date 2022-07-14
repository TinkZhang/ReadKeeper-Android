package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.basic.model.Platform
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun ReadingActionSection(
    book: Book,
    modifier: Modifier,
    onEditBookClick: () -> Unit = {},
    onAddProgressClick: () -> Unit = {}
) {
    if (book.platform == null) {
        FilledTonalButton(
            onClick = onEditBookClick,
            modifier = modifier
        ) {
            Text(text = stringResource(id = R.string.edit_book))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Filled.Edit, contentDescription = null)
        }
    } else {
        FilledTonalButton(
            onClick = onAddProgressClick,
            modifier = modifier
        ) {
            Text(text = stringResource(id = R.string.add_progress))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Filled.DataSaverOn, contentDescription = null)
        }
    }
}

@PreviewAnnotation
@Composable
private fun ReadingActionNew() {
    ReadKeeperTheme {
        Surface {
            ReadingActionSection(
                book = BookFactory.buildReadingSample(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun ReadingActionEdited() {
    ReadKeeperTheme {
        Surface {
            ReadingActionSection(
                book = BookFactory.buildReadingSample().copy(platform = Platform.PAPER),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun WishActionSection(
    modifier: Modifier = Modifier,
    onMoveToReadingClick: () -> Unit = {}
) {
    FilledTonalButton(
        onClick = onMoveToReadingClick,
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.add_reading))
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.Filled.BookmarkAdd, contentDescription = null)
    }
}

@PreviewAnnotation
@Composable
private fun WishActionSection() {
    ReadKeeperTheme {
        Surface {
            WishActionSection(modifier = Modifier.fillMaxWidth())
        }
    }
}

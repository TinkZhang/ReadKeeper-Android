package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.outlined.FavoriteBorder
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
fun SearchActionSection(
    modifier: Modifier = Modifier,
    onAddToWishClick: () -> Unit = {},
    onAddToReadingClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilledTonalButton(
            onClick = onAddToWishClick,
            modifier = Modifier.weight(1.0f)
        ) {
            Text(text = stringResource(id = R.string.add_wish))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
        }
        FilledTonalButton(
            onClick = onAddToReadingClick,
            modifier = Modifier.weight(1.0f)
        ) {
            Text(text = stringResource(id = R.string.add_reading))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Filled.BookmarkAdd, contentDescription = null)
        }
    }
}

@PreviewAnnotation
@Composable
private fun SearchActionSection() {
    ReadKeeperTheme {
        Surface {
            SearchActionSection(modifier = Modifier.fillMaxWidth())
        }
    }
}
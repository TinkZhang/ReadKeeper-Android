package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var isAddedToWish by remember { mutableStateOf(false) }
    var isAddedToReading by remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FilledTonalButton(
            onClick = {
                isAddedToWish = true
                onAddToWishClick()
            },
            modifier = Modifier.weight(1.0f)
        ) {
            Text(text = stringResource(id = R.string.add_wish))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                if (isAddedToWish) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Wish"
            )
        }
        FilledTonalButton(
            onClick = {
                isAddedToReading = true
                onAddToReadingClick()
            },
            modifier = Modifier.weight(1.0f)
        ) {
            Text(text = stringResource(id = R.string.add_reading))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                if (isAddedToReading) Icons.Filled.BookmarkAdded else Icons.Filled.BookmarkAdd,
                contentDescription = "Bookmark"
            )
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

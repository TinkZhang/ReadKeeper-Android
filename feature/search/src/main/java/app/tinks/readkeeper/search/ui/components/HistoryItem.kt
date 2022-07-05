package app.tinks.readkeeper.search.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchResultItem(
    text: String,
    image: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(image, contentDescription = null)
            Spacer(Modifier.width(16.dp))
            Text(
                text,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Icon(
            Icons.Default.NorthWest,
            contentDescription = null,
            modifier = Modifier.padding(start = 32.dp)
        )
    }
}

@PreviewAnnotation
@Composable
private fun SearchResultItemPreview() {
    ReadKeeperTheme {
        Surface {
            SearchResultItem(
                text = "Hello World", image = Icons.Default.History
            )
        }
    }
}

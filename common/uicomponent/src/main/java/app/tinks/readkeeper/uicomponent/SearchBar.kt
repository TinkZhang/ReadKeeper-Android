package app.tinks.readkeeper.uicomponent

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchBar(
    @StringRes text: Int, modifier: Modifier = Modifier, onClick: () -> Unit = {}
) {
    FilledTonalButton(
        modifier = modifier.fillMaxWidth(), colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
        ), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
                .alpha(0.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search, contentDescription = "Search"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                stringResource(id = text), style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun HomepageSearchBarPreview() {
    ReadKeeperTheme() {
        Surface() {
            SearchBar(text = R.string.home_search_bar)
        }
    }
}

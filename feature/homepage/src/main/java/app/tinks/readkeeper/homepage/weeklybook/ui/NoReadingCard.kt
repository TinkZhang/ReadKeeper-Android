package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.Section
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoReadingCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Section(title = stringResource(id = R.string.start_reading),
        modifier = modifier.clickable { onClick() }
    ) {
        Text(text = stringResource(id = R.string.search_to_add))
        Icon(
            Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
    }
}

@PreviewAnnotation
@Composable
private fun NoReadingCardPreview() {
    ReadKeeperTheme {
        Surface {
            NoReadingCard()
        }
    }
}

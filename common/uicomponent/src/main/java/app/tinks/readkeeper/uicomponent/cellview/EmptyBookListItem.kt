package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun EmptyBookListItem(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.desert),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.6f),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            stringResource(id = R.string.empty_book_list),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@PreviewAnnotation
@Composable
private fun EmptyBookListItem() {
    ReadKeeperTheme {
        Surface {
            EmptyBookListItem(modifier = Modifier.fillMaxSize())
        }
    }
}
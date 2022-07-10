package app.tinks.readkeeper.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.ReadingIconToggleButton
import app.tinks.readkeeper.uicomponent.WishIconToggleButton

@Composable
private fun SearchBookItemActionBar(
    modifier: Modifier = Modifier,
    onWishButtonClicked: () -> Unit = {},
    onReadingButtonClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var wishChecked by remember { mutableStateOf(false) }
        var readingChecked by remember { mutableStateOf(false) }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                "Add this book into: ",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ReadingIconToggleButton(
                checked = readingChecked
            ) {
                readingChecked = it
                onReadingButtonClicked()
            }
            WishIconToggleButton(checked = wishChecked) {
                wishChecked = it
                onWishButtonClicked()
            }
        }
    }
}

@Preview
@Composable
fun SearchBookItemActionBarPreview() {
    SearchBookItemActionBar()
}

package app.tinks.readkeeper.search.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.uicomponent.ReadingIconToggleButton
import app.tinks.readkeeper.uicomponent.WishIconToggleButton

@Composable
fun SearchCardBottom(
    modifier: Modifier = Modifier,
    onWishButtonClicked: (Boolean) -> Unit = {},
    onReadingButtonClicked: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var wishChecked by remember { mutableStateOf(false) }
        var readingChecked by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Add this book into: ",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )

        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ReadingIconToggleButton(
                checked = readingChecked
            ) {
                readingChecked = it
                onReadingButtonClicked(it)
            }
            WishIconToggleButton(checked = wishChecked) {
                wishChecked = it
                onWishButtonClicked(it)
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarBottomPreview() {
    SearchCardBottom(Modifier.fillMaxWidth())
}
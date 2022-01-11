package com.github.tinkzhang.wish.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WishCardEditBottom(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.End
    ) {
        FilledTonalButton(onClick = onButtonClicked) {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
            Icon(Icons.Default.BookmarkAdd, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Move to Reading List")
        }
    }
}

@Preview
@Composable
private fun WishCardEditBottomPreview() {
    WishCardEditBottom()
}

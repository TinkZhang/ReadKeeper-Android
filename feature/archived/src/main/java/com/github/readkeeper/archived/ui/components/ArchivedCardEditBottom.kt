package com.github.readkeeper.archived.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ArchivedCardEditBottom(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.End
    ) {
        FilledTonalButton(onClick = onButtonClicked) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Book")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Edit Book")
        }
    }
}

@Preview
@Composable
private fun ArchivedCardEditBottomPreview() {
    ArchivedCardEditBottom()
}

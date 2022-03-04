package com.github.tinkzhang.search.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun RkSearchTipItem(
    itemCount: Int,
    modifier: Modifier = Modifier
) {
    val message = when (itemCount) {
        0 -> "No book is found on Google, please try to search with book title"
        1 -> "You're lucky!"
        else -> ""
    }
    if (message.isNotEmpty()) {
        OutlinedCard(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun RkSearchTipItemPreview(
    @PreviewParameter(SearchItemNumberProvider::class) itemCount: Int
) {
    RkSearchTipItem(itemCount)
}

class SearchItemNumberProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(0, 1, 12)
}

package com.github.tinkzhang.search.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RkSearchTipItem(
    itemCount: Int,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    val message = when (itemCount) {
        0 -> "No book is found on Google, please try to search with book title"
        1 -> "You're lucky!"
        else -> ""
    }
    Text(text = message, modifier = Modifier)
}

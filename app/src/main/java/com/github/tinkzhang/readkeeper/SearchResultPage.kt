package com.github.tinkzhang.readkeeper

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SearchResultPage(keyword: String?) {
    Text(keyword ?: "")
}
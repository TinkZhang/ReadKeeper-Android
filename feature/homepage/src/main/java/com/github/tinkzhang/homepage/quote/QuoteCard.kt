package com.github.tinkzhang.homepage.quote

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuoteCard() {
    val quoteRepository = QuoteRepository(LocalContext.current)
    val randomQuote by quoteRepository.quote.collectAsState(null)
    if (randomQuote != null) {
        Card(onClick = { quoteRepository.reset() }, modifier = Modifier.fillMaxWidth()) {
            Text(randomQuote?.quote ?: "")
        }
    }
}
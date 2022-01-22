package com.github.tinkzhang.homepage.quote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.uicomponent.DpContentMediumPadding
import com.github.tinkzhang.uicomponent.Section
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuoteCard() {
    val context = LocalContext.current
    val quoteRepository = remember { QuoteRepository(context) }
    var randomId by remember { mutableStateOf(Random.nextInt(1, 106)) }
    val randomQuote by quoteRepository.getQuote(randomId).collectAsState(null)
    if (randomQuote != null) {
        QuoteStatelessCard(quote = randomQuote!!) {
            randomId = Random.nextInt(1, 106)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuoteStatelessCard(quote: Quote, onClicked: () -> Unit = {}) {
    Section(title = "\uD83E\uDD89 Random Quote") {
        Column(modifier = Modifier.clickable { onClicked() }) {
            Text(quote.quote)
            Spacer(modifier = Modifier.height(DpContentMediumPadding))
            Text(" --- ${quote.author}", Modifier.align(Alignment.End))
        }
    }
}

@Preview
@Composable
private fun QuoteCardPreview() {
    QuoteStatelessCard(quote = Quote(1, "Hello world", "Tink"))
}
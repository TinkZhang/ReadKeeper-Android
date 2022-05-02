package com.github.tinkzhang.homepage.quote

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.tinkzhang.uicomponent.DpContentMediumPadding
import com.github.tinkzhang.uicomponent.Section
import kotlin.random.Random

@ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
@Composable
private fun QuoteStatelessCard(quote: Quote, onClicked: () -> Unit = {}) {
    Section(title = "\uD83E\uDD89 Random Quote") {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .animateContentSize()) {
            Text(quote.quote)
            Spacer(modifier = Modifier.height(DpContentMediumPadding))
            Text(" --- ${quote.author}", Modifier.align(Alignment.End))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun QuoteCardPreview() {
    QuoteStatelessCard(quote = Quote(1, "Hello world", "Tink"))
}
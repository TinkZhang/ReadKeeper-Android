package com.github.tinkzhang.homepage.quote

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuoteCard() {
    val context = LocalContext.current
    val quoteRepository = remember { QuoteRepository(context) }
    var randomId by remember { mutableStateOf(Random.nextInt(1,106)) }
    val randomQuote by quoteRepository.getQuote(randomId).collectAsState(null)
    if (randomQuote != null) {
        QuoteStatelessCard(quote = randomQuote!!) {
            randomId = Random.nextInt(1, 106)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuoteStatelessCard(quote: Quote, onClicked: ()->Unit = {}) {
    Card(
        onClick = onClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Random Quote",
                    style = MaterialTheme.typography.titleMedium
                )
                Divider(Modifier.padding(vertical = 4.dp), thickness = 2.dp)
                Text(quote.quote)
                Spacer(Modifier.height(8.dp))
                Text(" --- ${quote.author}", Modifier.align(Alignment.End))
            }
        }
    }
}

@Preview
@Composable
private fun QuoteCardPreview() {
    QuoteStatelessCard(quote = Quote(1, "Hello world", "Tink"))
}
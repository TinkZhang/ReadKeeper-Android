package app.tinks.readkeeper.homepage.quote

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.uicomponent.DpContentMediumPadding
import app.tinks.readkeeper.uicomponent.Section
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
private fun QuoteStatelessCard(quote: Quote, onClick: () -> Unit = {}) {
    Section(title = stringResource(id = R.string.quote)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .animateContentSize()
            .testTag("Quote Card")
        ) {
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
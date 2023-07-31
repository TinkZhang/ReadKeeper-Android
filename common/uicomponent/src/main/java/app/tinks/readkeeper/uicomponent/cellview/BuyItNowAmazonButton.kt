package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.RkCustomTabClient
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun BuyItNowAmazonButton(
    link: String,
    client: RkCustomTabClient?,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.buy_now_on_amazon),
        contentDescription = "Amazon",
        contentScale = ContentScale.FillWidth,
        modifier = modifier.clickable { client?.launchTab(link) }
    )
}

@PreviewAnnotation
@Composable
private fun BuyItNowAmazonButton() {
    ReadKeeperTheme {
        Surface {
            BuyItNowAmazonButton(link = "", client = null)
        }
    }
}

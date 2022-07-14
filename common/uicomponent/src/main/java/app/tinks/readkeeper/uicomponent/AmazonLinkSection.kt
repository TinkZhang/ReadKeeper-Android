package app.tinks.readkeeper.uicomponent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun AmazonLinkSection(url: String?, mCustomTabClient: RkCustomTabClient?) {
    if (url.isNullOrBlank()) return
    Section(title = "Amazon Link") {

    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun AmazonLinkSectionPreview() {
    AmazonLinkSection(url = "www.amazon.com", mCustomTabClient = null)
}
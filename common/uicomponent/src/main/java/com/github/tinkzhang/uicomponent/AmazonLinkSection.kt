package com.github.tinkzhang.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmazonLinkSection(url: String?, mCustomTabClient: RkCustomTabClient?) {
    if (url.isNullOrBlank()) return
    Section(title = "Amazon Link") {
        FilledTonalButton(onClick = {
            mCustomTabClient?.launchTab(url)
        }) {
            Column(Modifier.fillMaxWidth()) {
                Image(
                    painterResource(id = R.drawable.ic_amazon_logo),
                    contentDescription = null,
                    Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(DpContentMediumPadding))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box {}
                    Text("Buy it from Amazon", style = MaterialTheme.typography.labelLarge)
                    Icon(
                        Icons.Default.Launch,
                        contentDescription = null,
                        Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun AmazonLinkSectionPreview() {
    AmazonLinkSection(url = "www.amazon.com", mCustomTabClient = null)
}
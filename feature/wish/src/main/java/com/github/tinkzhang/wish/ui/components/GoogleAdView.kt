package com.github.tinkzhang.wish.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun GoogleAdView(adSize: AdSize, adUnitId: String, modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier, factory = { content ->
        AdView(content).apply {
            this.adSize = adSize
            this.adUnitId = adUnitId
        }
    },
        update = { view ->
            view.loadAd(AdRequest.Builder().build())
        })
}

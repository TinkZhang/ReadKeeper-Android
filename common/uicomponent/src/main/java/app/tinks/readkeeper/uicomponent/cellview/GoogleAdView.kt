package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import app.tinks.readkeeper.uicomponent.DpVipSectionPadding
import app.tinks.readkeeper.uicomponent.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun GoogleAdView(
    adSize: AdSize,
    modifier: Modifier = Modifier,
    keyword: String = "",
) {
    val adUnitId: String = stringResource(id = R.string.google_ad_unit_id)
    AndroidView(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = DpVipSectionPadding),
        factory = { content ->
            AdView(content).apply {
                this.setAdSize(adSize)
                this.adUnitId = adUnitId
            }
        },
        update = { view ->
            view.loadAd(AdRequest.Builder().addKeyword(keyword).build())
        })
}

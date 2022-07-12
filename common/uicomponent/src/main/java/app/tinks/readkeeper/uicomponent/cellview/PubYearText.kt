package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PubYearText(
    year: Int,
    modifier: Modifier = Modifier
) {
    if (year in 1931..2099) {
        InfoText(
            text = year.toString(),
            modifier = modifier
        )
    }
}
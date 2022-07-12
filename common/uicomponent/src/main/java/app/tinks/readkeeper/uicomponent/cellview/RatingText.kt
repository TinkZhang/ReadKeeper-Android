package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun RatingText(
    rating: Double,
    modifier: Modifier = Modifier
) {
    if (rating != 0.0) {
        InfoText(
            text = "$rating ★",
            modifier = modifier
        )
    }
}

@PreviewAnnotation
@Composable
private fun RatingText() {
    ReadKeeperTheme {
        Surface {
            RatingText(rating = 4.2)
        }
    }
}

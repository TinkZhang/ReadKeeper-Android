package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InfoText(
    text: String?,
    modifier: Modifier = Modifier,
    maxLine: Int = 1,
    colorAlpha: Float = 0.8f
) {
    if (!text.isNullOrEmpty()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier,
            maxLines = maxLine,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = colorAlpha)
        )
    }
}
package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InfoText(
    text: String?,
    modifier: Modifier = Modifier,
    maxLine: Int = 1,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    if (!text.isNullOrEmpty()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier,
            maxLines = maxLine,
            color = color.copy(alpha = 0.8f)
        )
    }
}
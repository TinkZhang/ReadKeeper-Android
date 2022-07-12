package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.uicomponent.R

@Composable
fun ReadingProgressText(
    format: PageFormat,
    position: Int,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    val progressText = when (format) {
        PageFormat.PAGE -> stringResource(id = R.string.page, position)
        PageFormat.PERCENT_100 -> "${(position)}%"
        PageFormat.PERCENT_1000 -> "${(position / 10.0)}%"
        PageFormat.PERCENT_10000 -> "${position / 100.00}%"
    }
    Text(
        text = progressText,
        color = color,
        style = style
    )
}
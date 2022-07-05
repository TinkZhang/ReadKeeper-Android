package app.github.tinkzhang.uicomponent

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import app.tinks.readkeeper.basic.model.PageFormat
import com.github.tinkzhang.uicomponent.R

@Composable
fun ReadingProgressText(
    format: PageFormat,
    position: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    when (format) {
        PageFormat.PAGE -> Text(
            stringResource(id = R.string.page, position),
            style = textStyle
        )
        PageFormat.PERCENT_100 -> Text(
            "${(position)}%",
            style = textStyle
        )
        PageFormat.PERCENT_1000 -> Text(
            "${(position / 10.0)}%",
            style = textStyle
        )
        PageFormat.PERCENT_10000 -> Text(
            "${position / 100.00}%",
            style = textStyle
        )
    }
}
package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.basic.model.PageFormat
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun PageText(
    pageFormat: PageFormat,
    modifier: Modifier = Modifier,
    realPage: Int = 0
) {
    if (pageFormat == PageFormat.PAGE && realPage > 0) {
        InfoText(
            text = "$realPage ${stringResource(id = R.string.pages)}",
            modifier = modifier
        )
    }
}

@PreviewAnnotation
@Composable
private fun PageTextPagePreview() {
    ReadKeeperTheme {
        Surface {
            PageText(pageFormat = PageFormat.PAGE, realPage = 123)
        }
    }
}

@PreviewAnnotation
@Composable
private fun PageTextPercentagePreview() {
    ReadKeeperTheme {
        Surface {
            PageText(pageFormat = PageFormat.PERCENT_1000)
        }
    }
}
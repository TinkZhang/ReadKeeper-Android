package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.tinks.readkeeper.uicomponent.DpContentMediumPadding
import app.tinks.readkeeper.uicomponent.DpDividerMedium

@Composable
fun DetailTitleSection(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        header()
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                ),
                modifier = Modifier.alpha(0.85f)
            )
        }
        Divider(
            Modifier.padding(vertical = DpContentMediumPadding),
            thickness = DpDividerMedium
        )
        content()
    }
}

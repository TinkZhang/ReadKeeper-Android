package app.tinks.readkeeper.uicomponent.detail

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.cellview.History
import app.tinks.readkeeper.uicomponent.detail.actionsection.DetailTitleSection

@Composable
fun HistorySection(
    records: List<Record>,
    modifier: Modifier = Modifier,
) {
    DetailTitleSection(
        modifier = modifier.height(240.dp),
        title = stringResource(id = R.string.history)
    ) {
        History(records)
    }
}

package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.uicomponent.Section
import app.tinks.readkeeper.uicomponent.cellview.History
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllHistoryCard(
    records: List<Record>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Section(
        title = stringResource(id = R.string.reading_history),
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable { onClick() }
    ) {
        History(records = records)
    }
}
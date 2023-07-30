package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.uicomponent.Section

@ExperimentalMaterial3Api
@Composable
fun DescriptionSection(description: String?) {
    if (!description.isNullOrBlank()) {
        Section(title = stringResource(id = R.string.description)) {
            Text(description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
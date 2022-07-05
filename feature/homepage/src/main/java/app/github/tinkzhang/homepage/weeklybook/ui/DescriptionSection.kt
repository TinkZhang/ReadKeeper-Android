package app.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.github.tinkzhang.uicomponent.Section

@ExperimentalMaterial3Api
@Composable
fun DescriptionSection(description: String?) {
    if (!description.isNullOrBlank()) {
        Section(title = "Description") {
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
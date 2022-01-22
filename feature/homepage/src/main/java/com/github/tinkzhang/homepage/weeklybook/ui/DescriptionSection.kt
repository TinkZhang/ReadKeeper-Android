package com.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.tinkzhang.uicomponent.Section

@Composable
fun DescriptionSection(description: String?) {
    if (!description.isNullOrBlank()) {
        Section(title = "Description") {
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
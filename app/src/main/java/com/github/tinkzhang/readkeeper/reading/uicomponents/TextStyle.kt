package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextStyleDemo() {
    androidx.compose.material3.Surface() {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Display Large", style = MaterialTheme.typography.displayLarge)
            Text(text = "Display Medium", style = MaterialTheme.typography.displayMedium)
            Text(text = "Display Small", style = MaterialTheme.typography.displaySmall)
            Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Headline Medium", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Headline Small", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Title Large", style = MaterialTheme.typography.titleLarge)
            Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
            Text(text = "Title Small", style = MaterialTheme.typography.titleSmall)
            Text(text = "Label Large", style = MaterialTheme.typography.labelLarge)
            Text(text = "Label Medium", style = MaterialTheme.typography.labelMedium)
            Text(text = "Label Small", style = MaterialTheme.typography.labelSmall)
            Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Body Small", style = MaterialTheme.typography.bodySmall)
        }
    }

}

@Preview
@Composable
fun FontDemo() {
    TextStyleDemo()
}
package com.github.tinkzhang.uicomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReadingProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0.0f
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    Canvas(modifier = modifier) {
        drawRect(color = primaryColor, alpha = 0.1f)
        drawRect(color = tertiaryColor, size = this.size.copy(width = this.size.width * progress) )
    }
}

@Preview
@Composable
private fun ProgressBarPreview() {
    ReadingProgressBar(Modifier.width(128.dp).height(16.dp), progress = 0.45f)
}

package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RkProgressCircle(modifier: Modifier = Modifier, progress: Float = 0.0f) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val progressDegree = progress * 360.0f
    Canvas(modifier = modifier) {
        val width = 0.2f
        drawCircle(
            primaryColor,
            style = Stroke()
        )
        drawCircle(
            primaryColor,
            radius = size.minDimension / 2.0f * (1.0f - 2.0f * width),
            style = Stroke()
        )
        val path = Path().apply {
            addArc(
                Rect(Offset.Zero, size = this@Canvas.size),
                startAngleDegrees = -90.0f + progressDegree,
                sweepAngleDegrees = -progressDegree
            )
            arcTo(
                Rect(
                    Offset.Zero.copy(size.minDimension * width, size.minDimension * width),
                    size = this@Canvas.size.copy(
                        width = size.minDimension * (1.0f - 2.0f * width),
                        height = size.minDimension * (1.0f - 2.0f * width)
                    )
                ),
                startAngleDegrees = -90.0f,
                sweepAngleDegrees = progressDegree,
                forceMoveTo = false
            )
        }
        drawPath(path, tertiaryColor)
    }
}

@Preview
@Composable
fun RkProgressCirclePreview() {
    RkProgressCircle(0.4f, Modifier.size(48.dp))
}
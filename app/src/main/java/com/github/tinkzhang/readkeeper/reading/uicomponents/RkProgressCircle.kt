package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.data.PageFormat

@Composable
fun RkProgress(
    format: PageFormat = PageFormat.PAGE,
    position: Int,
    totalPages: Int,
    size: Dp = 64.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RkProgressCircle(
            progress = position.toFloat() / totalPages.toFloat(),
            modifier = Modifier.size(size)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RkProgressText(format = format, position = position, textStyle = textStyle)
    }
}

@Preview
@Composable
fun RkProgressPreview() {
    RkProgress(position = 123, totalPages = 456)
}

@Preview
@Composable
fun RkProgressPercentagePreview() {
    RkProgress(format = PageFormat.PERCENT_10000, position = 133, totalPages = 10000)
}

@Composable
fun RkProgressText(
    format: PageFormat,
    position: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge
) {
    when (format) {
        PageFormat.PAGE -> Text(
            stringResource(id = R.string.page, position),
            style = textStyle
        )
        PageFormat.PERCENT_100 -> Text(
            "${(position)}%",
            style = textStyle
        )
        PageFormat.PERCENT_1000 -> Text(
            "${(position / 10.0)}%",
            style = textStyle
        )
        PageFormat.PERCENT_10000 -> Text(
            "${position / 100.00}%",
            style = textStyle
        )
    }
}

@Composable
fun RkProgressCircle(
    modifier: Modifier = Modifier,
    progress: Float = 0.0f
) {
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
    RkProgressCircle(Modifier.size(144.dp), 0.6f)
}
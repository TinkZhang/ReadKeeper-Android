package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import app.tinks.readkeeper.basic.model.Record
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit.DAYS

data class Contribution(
    val pages: Int = 0, val day: LocalDate
)

@Composable
fun History(records: List<Record>) {
    if (records.isEmpty()) return
    val contribution = remember(records.size) {
        generateContributions(records)
    }
    ReadingHistory(contributions = contribution)
}

fun generateContributions(records: List<Record>): List<Contribution> {
    val time = records.minByOrNull { it.timestamp }?.timestamp
    val firstDay =
        Instant.ofEpochMilli(time?.seconds?.times(1000) ?: 0).atZone(ZoneId.systemDefault())
            .toLocalDate()
    var day = firstDay
    val today = LocalDate.now()
    val contributions: MutableList<Contribution> = mutableListOf()
    while (!day.isAfter(today)) {
        contributions.add(Contribution(day = day))
        day = day.plusDays(1)
    }
    for (record in records) {
        val index = DAYS.between(firstDay, Instant.ofEpochMilli(record.timestamp.seconds.times(1000))
            .atZone(ZoneId.systemDefault()).toLocalDate()).toInt()
        contributions[index] = Contribution(
            pages = contributions[index].pages + record.endPage - record.startPage,
            day = contributions[index].day
        )
    }
    return contributions
}

@Composable
fun ReadingHistory(contributions: List<Contribution>) {
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val firstDay = contributions.first().day.dayOfYear
    val firstDayOfWeek = contributions.first().day.dayOfWeek
    var flag: Float by remember {
        mutableStateOf(0.0f)
    }
    val animatedColorFactor by animateFloatAsState(
        targetValue = flag, animationSpec = tween(1800), label = ""
    )
    LaunchedEffect(key1 = flag) {
        flag = 4.0f
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxColumns = (this.drawContext.size.width / 80).toInt() * 7
        val displayContributions =
            if (contributions.size > maxColumns) contributions.drop(contributions.size - maxColumns) else contributions
        for (contribution in displayContributions) {
            val offset = Offset(
                y = (contribution.day.dayOfWeek.value - 1) * 80.0f,
                x = (contribution.day.dayOfYear - firstDay + firstDayOfWeek.value - 1) / 7 * 80.0f
            )
            val style: DrawStyle = if (contribution.pages == 0) Stroke(width = 8.0f) else Fill
            val color = tertiaryColor.copy(
                alpha = minOf(
                    contribution.pages * animatedColorFactor / 1000f + 0.2f, 1.0f
                )
            )
            drawRoundRect(
                color = color,
                topLeft = offset,
                size = Size(60.0f, 60.0f),
                style = style,
                cornerRadius = CornerRadius(1.0f, 1.0f)
            )
        }
    }
}

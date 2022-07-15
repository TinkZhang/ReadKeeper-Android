package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingFlat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.NYTimesBookSample

@Composable
fun WeeklyVipMetadata(
    book: NYTimesBook,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RankStat(book)
        if (book.author.isNotEmpty()) {
            Text(
                "✍️  " + book.author,
                style = if (book.author.length > 40) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                maxLines = 3,
            )
        }
    }
}

@Composable
private fun RankStat(book: NYTimesBook) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
         Text(
            text = "# ${book.rank}",
            style = MaterialTheme.typography.titleMedium,
        )
        val change = if (book.rankLastWeek != 0) {
            book.rankLastWeek - book.rank
        } else {
            15 - book.rank
        }
        when {
            change < 0 -> {
                Row {
                    Icon(
                        Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        " $change",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            change > 0 -> {
                Row() {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        " +$change",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            else -> {
                Icon(
                    Icons.Default.TrendingFlat,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
        if (book.weeksOnList != 0) {
            Row() {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    "${book.weeksOnList} weeks",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }

    }
}

@Preview
@Composable
private fun WeeklyVipMetadata() {
    WeeklyVipMetadata(book = NYTimesBookSample())
}

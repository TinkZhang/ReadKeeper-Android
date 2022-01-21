package com.github.tinkzhang.homepage.weeklybook.ui

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
import com.github.tinkzhang.basic.model.NYTimesBook
import com.github.tinkzhang.basic.model.NYTimesBookSample

@Composable
fun WeeklyVipMetadata(
    book: NYTimesBook
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        RankStat(book)
        Spacer(modifier = Modifier.height(16.dp))
        if (book.author.isNotEmpty()) {
            Text(
                "✍️  " + book.author,
                style = if (book.author.length > 40) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
                maxLines = 3,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Composable
private fun RankStat(book: NYTimesBook) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "# ${book.rank}",
            style = MaterialTheme.typography.titleLarge,
        )
        val change = book.rankLastWeek - book.rank
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
                        style = MaterialTheme.typography.titleLarge,
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
                        " $change",
                        style = MaterialTheme.typography.titleLarge,
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
                    style = MaterialTheme.typography.titleLarge,
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
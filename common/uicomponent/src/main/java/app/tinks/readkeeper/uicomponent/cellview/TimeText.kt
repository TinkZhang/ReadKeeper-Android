package app.tinks.readkeeper.uicomponent.cellview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.R
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimeText(time: Timestamp, isLongFormat: Boolean = false) {
    val timeDiffString = if (isLongFormat) {
        when (val diff = Timestamp.now().seconds - time.seconds) {
            in 0..60 -> pluralStringResource(R.plurals.added_second_ago, count = diff.toInt(), diff)
            in 60..60 * 60 -> pluralStringResource(
                R.plurals.added_minute_ago,
                (diff / 60).toInt(),
                diff / 60
            )
            in 60 * 60..60 * 60 * 24 -> pluralStringResource(
                R.plurals.added_hour_ago,
                (diff / 60 / 60).toInt(),
                diff / 60 / 60
            )
            in 60 * 60 * 24..60 * 60 * 24 * 100 -> pluralStringResource(
                R.plurals.added_day_ago,
                (diff / 60 / 60 / 24).toInt(),
                diff / 60 / 60 / 24
            )
            else -> stringResource(
                id = R.string.added_time, SimpleDateFormat(
                    "yyyy-MM-dd", Locale(
                        androidx.compose.ui.text.intl.Locale.current.language,
                        androidx.compose.ui.text.intl.Locale.current.region
                    )
                ).format(time.toDate())
            )
        }
    } else {
        when (val diff = Timestamp.now().seconds - time.seconds) {
            in 0..60 -> stringResource(id = R.string.second, diff)
            in 60..60 * 60 -> stringResource(id = R.string.minute, diff / 60)
            in 60 * 60..60 * 60 * 24 -> stringResource(id = R.string.hour, diff / 60 / 60)
            in 60 * 60 * 24..60 * 60 * 24 * 100 -> stringResource(
                id = R.string.day, diff / 60 / 60 / 24
            )

            else -> SimpleDateFormat(
                "yyyy-MM-dd", Locale(
                    androidx.compose.ui.text.intl.Locale.current.language,
                    androidx.compose.ui.text.intl.Locale.current.region
                )
            ).format(time.toDate())
        }
    }
    InfoText(text = timeDiffString)
}

@PreviewAnnotation
@Composable
private fun TimeTextSecond() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(time = Timestamp(now - 23, 0))
        }
    }
}

@PreviewAnnotation
@Composable
private fun TimeTextSecondLong() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(
                time = Timestamp(now - 1, 0),
                isLongFormat = true
            )
        }
    }
}

@PreviewAnnotation
@Composable
private fun TimeTextMinute() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(time = Timestamp(now - 23 * 60, 0))
        }
    }
}

@PreviewAnnotation
@Composable
private fun TimeTextHour() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(time = Timestamp(now - 23 * 60 * 60, 0))
        }
    }
}


@PreviewAnnotation
@Composable
private fun TimeTextDay() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(time = Timestamp(now - 48 * 60 * 60, 0))
        }
    }
}

@PreviewAnnotation
@Composable
private fun TimeTextDate() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(time = Timestamp(now - 200 * 24 * 60 * 60, 0))
        }
    }
}

@PreviewAnnotation
@Composable
private fun TimeTextDateLong() {
    ReadKeeperTheme {
        Surface {
            val now = Timestamp.now().seconds
            TimeText(
                time = Timestamp(now - 200 * 24 * 60 * 60, 0),
                isLongFormat = true
            )
        }
    }
}
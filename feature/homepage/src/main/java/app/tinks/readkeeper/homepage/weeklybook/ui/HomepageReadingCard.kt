package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.Section
import app.tinks.readkeeper.uicomponent.list.BookListCard
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageReadingCard(
    book: Book?,
    onAddProgressClick: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    if (book == null) return
    Section(title = stringResource(id = R.string.reading_card)) {
        BookListCard(
            book = book,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            onAddProgressClicked = onAddProgressClick,
        )
    }
}

@PreviewAnnotation
@Composable
private fun HomepageReadingCardPreview() {
    ReadKeeperTheme {
        Surface {
            HomepageReadingCard(BookFactory.buildReadingSample())
        }
    }
}

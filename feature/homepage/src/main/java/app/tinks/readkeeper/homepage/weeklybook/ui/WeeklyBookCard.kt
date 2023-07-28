package app.tinks.readkeeper.homepage.weeklybook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.NYTimesBookSample
import app.tinks.readkeeper.homepage.R
import app.tinks.readkeeper.homepage.weeklybook.HomepageViewModel
import app.tinks.readkeeper.uicomponent.Section
import app.tinks.readkeeper.uicomponent.cellview.BookCardImage

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun WeeklyBookCard(
    type: NYBookType,
    onBookClicked: (String) -> Unit = {},
    viewModel: HomepageViewModel = viewModel(),
) {
    Section(header = {
        Column() {
            Icon(
                painter = painterResource(id = R.drawable.ic_newyorktimes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(id = R.string.weekly_best_seller, type.name),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }) {
        val books by when (type) {
            NYBookType.Fictions -> viewModel.fictionBooks.collectAsState()
            NYBookType.NonFictions -> viewModel.nonFictionBooks.collectAsState()
        }
        LazyRow() {
            items(books) { book: NYTimesBook ->
                NYTimesBookCard(book, onBookClicked)
            }
        }
    }
}


@Preview
@Composable
private fun WeeklyBookCardPreview() {
    WeeklyBookCard(
        NYBookType.Fictions
    )
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun NYTimesBookCard(
    book: NYTimesBook,
    onBookClicked: (String) -> Unit = {},
) {
    ElevatedCard(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable {
                onBookClicked(book.title)
            },
    ) {
        Box() {
            BookCardImage(url = book.bookImage, title = book.title)
            Text(
                "# ${book.rank}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(2.dp),
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Preview
@Composable
private fun NYTimesBookCardPreview() {
    NYTimesBookCard(book = NYTimesBookSample())
}

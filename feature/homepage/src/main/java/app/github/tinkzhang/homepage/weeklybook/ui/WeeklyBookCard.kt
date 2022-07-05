package app.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import app.github.tinkzhang.uicomponent.BookCardImage
import app.github.tinkzhang.uicomponent.Section
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.NYTimesBookSample

@OptIn(ExperimentalMaterialApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun WeeklyBookCard(
    type: NYBookType,
    navController: NavController? = null,
    viewModel: WeeklyBookViewModel = viewModel(),
) {
    Section(header = {
        Column() {
            Icon(
                painter = painterResource(id = com.github.tinkzhang.homepage.R.drawable.ic_newyorktimes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Weekly Best Seller - ${type.name}",
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
                NYTimesBookCard(book, navController)
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

@OptIn(ExperimentalMaterialApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun NYTimesBookCard(
    book: NYTimesBook,
    navController: NavController? = null
) {
    ElevatedCard(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable {
                navController?.navigate("weekly_item/${book.title}")
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

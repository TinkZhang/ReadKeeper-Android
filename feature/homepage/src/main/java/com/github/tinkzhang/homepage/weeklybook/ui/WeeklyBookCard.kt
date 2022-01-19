package com.github.tinkzhang.homepage.weeklybook.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.basic.model.NYTimesBook
import com.github.tinkzhang.basic.model.NYTimesBookSample
import com.github.tinkzhang.homepage.weeklybook.WeeklyBookViewModel
import com.github.tinkzhang.uicomponent.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeeklyBookCard(
    type: NYBookType,
    viewModel: WeeklyBookViewModel = viewModel(),
    onClicked: () -> Unit = {}
) {
    Card(
        onClick = onClicked,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "New York Times Weekly Best Seller - ${type.name}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Divider(Modifier.padding(vertical = 8.dp), thickness = 2.dp)
                val books by when (type) {
                        NYBookType.Fiction -> viewModel.fictionBooks.collectAsState()
                        NYBookType.NonFiction -> viewModel.nonFictionBooks.collectAsState()
                    }
                LazyRow() {
                    items(books) { book: NYTimesBook ->
                        NYTimesBookCard(book)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun WeeklyBookCardPreview() {
    WeeklyBookCard(
        NYBookType.Fiction
    )
}

@Composable
fun NYTimesBookCard(book: NYTimesBook) {
    Card(
        Modifier.padding(end = 8.dp),
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.width(96.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = book.bookImage,
                    builder = {
                        this.crossfade(true)
                        placeholder(drawableResId = R.drawable.ic_launcher_foreground)
                    }
                ),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )
            Text(
                book.title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                maxLines = 3,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun NYTimesBookCardPreview() {
    NYTimesBookCard(book = NYTimesBookSample())
}


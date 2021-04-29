package com.github.tinkzhang.readkeeper.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun SearchBookItem(book: SearchBook) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberCoilPainter(request = book.imageUrl),
                contentDescription = book.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(0.314f)
                    .fillMaxWidth()
            )
            Column(modifier = Modifier
                .weight(0.686f)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)) {
                Text(
                    book.title,
                    style = MaterialTheme.typography.h6,
                    maxLines = 3,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Text(
                    "✍️   " + book.author,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                Text(
                    "\uD83D\uDCC5   " + book.originalPublicationYear.toString(),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchBookItemPrev(book: SearchBook = SearchBook().buildSample()) {
    SearchBookItem(book = book)
}


@Preview
@Composable
fun SearchBookItemListPrev(book: SearchBook = SearchBook().buildSample()) {
    Column() {
        SearchBookItem(book = book)
        SearchBookItem(book = book)
        SearchBookItem(book = book)
        SearchBookItem(book = book)
    }
}
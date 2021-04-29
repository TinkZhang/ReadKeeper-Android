package com.github.tinkzhang.readkeeper.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.R
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
                Row(modifier = Modifier.align(Alignment.End)) {
                    var wishChecked by remember { mutableStateOf(false) }
                    var readingChecked by remember { mutableStateOf(false) }
                    IconToggleButton(
                        checked = wishChecked,
                        onCheckedChange = { wishChecked = it },
                    ) {
                        if (wishChecked) {
                            Icon(
                                Icons.Filled.Favorite,
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = stringResource(id = R.string.wished)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.add_wish)
                            )
                        }
                    }
                    IconToggleButton(
                        checked = readingChecked,
                        onCheckedChange = { readingChecked = it },
                    ) {
                        if (readingChecked) {
                            Icon(
                                Icons.Filled.BookmarkAdded,
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = stringResource(id = R.string.reading_added)
                            )
                        } else {
                            Icon(
                                Icons.Outlined.BookmarkBorder,
                                contentDescription = stringResource(id = R.string.add_reading)
                            )
                        }
                    }
                }
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
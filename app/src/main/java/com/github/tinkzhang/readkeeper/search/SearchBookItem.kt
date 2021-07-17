package com.github.tinkzhang.readkeeper.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.ui.components.ReadingIconToggleButton
import com.github.tinkzhang.readkeeper.ui.components.WishIconToggleButton
import com.github.tinkzhang.readkeeper.ui.theme.ReadKeeperTheme
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun SearchBookItem(book: SearchBook) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberCoilPainter(
                        request = book.imageUrl,
                        previewPlaceholder = R.drawable.ic_launcher_foreground,
                    ),
                    contentDescription = book.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .weight(0.314f)
                        .fillMaxWidth()
                )
                SearchBookItemMetadata(
                    book = book,
                    modifier = Modifier.weight(0.684f),
                )
            }
            Divider()
            SearchBookItemActionBar()
        }
    }
}

@Composable
fun SearchBookItemMetadata(
    book: SearchBook,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            book.title,
            style = MaterialTheme.typography.h5,
            maxLines = 3,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Column(verticalArrangement = Arrangement.Bottom) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "✍️   " + book.author,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "\uD83D\uDCC5   " + book.originalPublicationYear.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
        }

    }
}

@Composable
private fun SearchBookItemActionBar(
    modifier: Modifier = Modifier,
    onWishButtonClicked: () -> Unit = {},
    onReadingButtonClicked: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var wishChecked by remember { mutableStateOf(false) }
        var readingChecked by remember { mutableStateOf(false) }
        ReadingIconToggleButton(checked = readingChecked) {
            readingChecked = it
            onReadingButtonClicked()
        }
        WishIconToggleButton(checked = wishChecked) {
            wishChecked = it
            onWishButtonClicked()
        }
    }
}

@Preview
@Composable
fun SearchBookItemActionBarPreview() {
    ReadKeeperTheme {
        SearchBookItemActionBar()
    }
}

@Preview
@Composable
fun SearchBookItemPrev(book: SearchBook = SearchBook().buildSample()) {
    ReadKeeperTheme() {
        SearchBookItem(book = book)
    }
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
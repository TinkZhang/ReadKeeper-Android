package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.data.BookInfo
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.common.data.ReadingBookFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingItemScreen(
    uuid: String,
    readingViewModel: ReadingViewModel,
    navController: NavController
) {
    val book = readingViewModel.getBook(uuid)
    Scaffold(topBar = {
        MediumTopAppBar(title = { Text(book.bookInfo.title) }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Progress", color = MaterialTheme.colorScheme.onPrimaryContainer) },
            onClick = { /*TODO*/ },
            icon = {
                Icon(
                    Icons.Default.DataSaverOn,
                    contentDescription = "Add Reading Progress",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            })
    }) {
        Column(modifier = Modifier.fillMaxWidth()) {
            RkBookInfoSection(book = book)
        }
    }

}

@Composable
fun RkBookInfoSection(book: ReadingBook) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier.weight(1.0f)) {
            Image(
                painter = rememberImagePainter(
                    data = book.bookInfo.imageUrl,
                    builder = {
                        placeholder(R.drawable.book_sample)
                    }),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
        RkBookInfoMetadata(book = book.bookInfo, modifier = Modifier.weight(2.0f))
    }
}

@Composable
fun RkBookInfoMetadata(book: BookInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (book.author.isNotEmpty()) {
            Text(
                "✍️   Author: " + book.author,
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (book.rating != 0.0) {
            Text(
                text = "⭐   Rating: ${book.rating} of 10",
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (book.pubYear != 0) {
            Text(
                "\uD83D\uDDD3️   Published in ${book.pubYear}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 240)
@Composable
fun RkBookInfoSectionPreview() {
    RkBookInfoSection(book = ReadingBookFactory.buildSample())
}

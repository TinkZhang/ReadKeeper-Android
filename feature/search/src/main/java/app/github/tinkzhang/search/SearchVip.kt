package app.github.tinkzhang.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.github.tinkzhang.uicomponent.ReadingIconToggleButton
import app.github.tinkzhang.uicomponent.WishIconToggleButton
import app.tinks.readkeeper.basic.model.SearchBook
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.github.tinkzhang.search.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchVip(book: SearchBook) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(
                        data = book.bookInfo.imageUrl,
                        imageLoader = LocalImageLoader.current,
                        builder = {
                            this.crossfade(true)
                            placeholder(drawableResId = R.drawable.ic_launcher_foreground)
                        }
                    ),
                    contentDescription = book.bookInfo.title,
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
            book.bookInfo.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 3,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Column(verticalArrangement = Arrangement.Bottom) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "✍️   " + book.bookInfo.author,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    "\uD83D\uDCC5   " + book.bookInfo.pubYear.toString(),
                    style = MaterialTheme.typography.labelMedium,
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
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                "Add this book into: ",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        Row(
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ReadingIconToggleButton(
                checked = readingChecked
            ) {
                readingChecked = it
                onReadingButtonClicked()
            }
            WishIconToggleButton(checked = wishChecked) {
                wishChecked = it
                onWishButtonClicked()
            }
        }
    }
}

@Preview
@Composable
fun SearchBookItemActionBarPreview() {
    SearchBookItemActionBar()
}

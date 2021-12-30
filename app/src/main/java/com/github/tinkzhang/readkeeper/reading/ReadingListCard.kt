package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.common.data.ReadingBookFactory
import com.github.tinkzhang.readkeeper.reading.uicomponents.RkProgressBar
import com.github.tinkzhang.readkeeper.reading.uicomponents.RkProgressText
import com.github.tinkzhang.readkeeper.ui.components.RkCategoryChip

@Composable
fun ReadingListCard(book: ReadingBook, navController: NavController? = null) {
    Card(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { navController?.navigate("reading_item/${book.uuid}?open_progress_dialog=${false}") },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BookCardImage(book = book)
                    ReadingCardMetadata(book = book)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Divider()
                Spacer(modifier = Modifier.width(8.dp))
                if (book.formatEdited) {
                    ReadingCardProgressBottom(book = book)
                } else {
                    ReadingCardEditBottom(book = book)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReadingCard() {
    ReadingListCard(book = ReadingBookFactory.buildSample())
}

@Composable
fun BookCardImage(book: ReadingBook, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(
            data = book.bookInfo.imageUrl,
            builder = {
                this.crossfade(true)
                placeholder(drawableResId = R.drawable.ic_launcher_foreground)
            }
        ),
        contentDescription = book.bookInfo.title,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(128.dp)
    )
}

@Preview
@Composable
private fun BookCardImagePreview() {
    BookCardImage(book = ReadingBookFactory.buildSample())
}

@Composable
fun ReadingCardMetadata(
    book: ReadingBook,
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
            style = MaterialTheme.typography.titleLarge,
            maxLines = 3,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Column(verticalArrangement = Arrangement.Bottom) {
            Text(
                "✍️   " + book.bookInfo.author,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            RkCategoryChip(category = book.category ?: "", isSelected = true)
        }

    }
}

@Preview
@Composable
private fun ReadingCardMetadataPreview() {
    ReadingCardMetadata(book = ReadingBookFactory.buildSample())
}

@Composable
fun ReadingCardEditBottom(book: ReadingBook, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.End
    ) {
        FilledTonalButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Book")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Edit Book")
        }
    }
}

@Preview
@Composable
private fun ReadingCardEditBottomPreview() {
    ReadingCardEditBottom(book = ReadingBookFactory.buildSample())
}

@Composable
fun ReadingCardProgressBottom(book: ReadingBook, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.weight(4.0f)) {
            Icon(
                painterResource(id = book.platform.icon),
                contentDescription = book.platform.label,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.fillMaxWidth()) {
                RkProgressText(
                    format = book.pageFormat,
                    position = book.records.lastOrNull()?.endPage ?: 0,
                    textStyle = MaterialTheme.typography.titleMedium,
                )
                RkProgressBar(
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                    progress = book.records.lastOrNull()?.endPage?.toFloat()
                        ?.div(book.bookInfo.pages.toFloat()) ?: 0.0f
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(onClick = { /*TODO*/ }, Modifier.weight(1.0f)) {
            Icon(Icons.Default.DataSaverOn, contentDescription = "Add Progress")
        }
    }
}

@Preview
@Composable
private fun ReadingCardBottomPreview() {
    ReadingCardProgressBottom(book = ReadingBookFactory.buildSample())
}
package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.ui.components.RkCategoryChip

@Composable
fun ReadingBookItem(book: ReadingBook) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberImagePainter(
                        data = book.imageUrl,
                        imageLoader = LocalImageLoader.current,
                        builder = {
                            this.crossfade(true)
                            placeholder(drawableResId = R.drawable.ic_launcher_foreground)
                        }
                    ),
                    contentDescription = book.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxWidth()
                )
                ReadingBookItemMetadata(
                    book = book,
                    modifier = Modifier.weight(0.8f),
                )
            }
            Divider()
            RkProgress(
                records = book.readingRecords,
                platform = book.platform,
                recordFormat = book.recordFormat,
                page = book.pages,
                onInitProgressClicked = { /*TODO*/ },
                onRealProgressClicked = { /*TODO*/ })
            Divider()
            RkShortNote(
                note = book.readingNotes.last(),
                onAddButtonClicked = {},
            )
        }
    }
}

@Composable
fun ReadingBookItemMetadata(
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
                RkCategoryChip(category = book.labels.first(), isSelected = true)
            }
        }

    }
}

@Composable
fun RkProgress(
    records: List<ReadingRecord>,
    platform: ReadingPlatform,
    recordFormat: RecordFormat,
    page: Int,
    onInitProgressClicked: () -> Unit,
    onRealProgressClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (records.isEmpty()) {
        Button(onClick = onInitProgressClicked) {
            Text("Start to track Progress")
        }
    } else {
        RkRealProgress(records, platform, recordFormat, page, onRealProgressClicked, modifier)
    }
}

@Composable
fun RkRealProgress(
    records: List<ReadingRecord>,
    platform: ReadingPlatform,
    recordFormat: RecordFormat,
    page: Int,
    onRealProgressClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        Icon(
            painter = painterResource(id = platform.icon),
            contentDescription = platform.label,
        )
        Column {
            val progress = records.last().endPage
            RkProgressBar(progress = progress, total = page)
            Text(
                when (recordFormat) {
                    RecordFormat.PAGE -> "$progress of $page"
                    RecordFormat.PERCENTAGE -> "${progress / 10000.0}%"
                }
            )
            Icon(Icons.Default.Addchart, contentDescription = "Add reading progress")
        }
    }
}

@Composable
fun RkProgressBar(progress: Int, total: Int) {

}

@Composable
fun RkShortNote(
    note: ReadingNote,
    onAddButtonClicked: () -> Unit,
) {
    Column() {
        Text(note.note)
        Button(onClick = onAddButtonClicked) {
            Icon(Icons.Default.AddComment, contentDescription = "Add reading note")
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Add Note")
        }
    }
}
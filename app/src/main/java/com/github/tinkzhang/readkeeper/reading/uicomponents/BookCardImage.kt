package com.github.tinkzhang.readkeeper.reading.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.common.data.ReadingBook
import com.github.tinkzhang.readkeeper.common.data.ReadingBookFactory

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

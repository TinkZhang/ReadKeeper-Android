package app.tinks.readkeeper.uicomponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import app.tinks.readkeeper.basic.model.BookFactory
import coil.compose.rememberImagePainter

@Composable
fun BookCardImageSmall(
    url: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    BookCardImage(
        url = url,
        title = title,
        modifier = modifier,
        width = 75.0f
    )
}

@Composable
fun BookCardImageLarge(
    url: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    BookCardImage(
        url = url,
        title = title,
        modifier = modifier,
        width = 125.0f
    )
}

@Composable
fun BookCardImage(
    url: String,
    title: String,
    modifier: Modifier = Modifier,
    width: Float = 100.0f
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                this.crossfade(true)
                placeholder(drawableResId = R.drawable.ic_launcher_foreground)
            }
        ),
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(Dp(width))
            .height(Dp(width * 1.5f))
    )
}

@Preview
@Composable
private fun BookCardImagePreview() {
    val book = BookFactory.buildReadingSample()
    BookCardImage(url = book.basicInfo.imageUrl, title = book.basicInfo.title)
}

package app.tinks.readkeeper.uicomponent.list.bookinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.InfoText
import app.tinks.readkeeper.uicomponent.cellview.PageText
import app.tinks.readkeeper.uicomponent.cellview.PubYearText
import app.tinks.readkeeper.uicomponent.cellview.RatingText
import app.tinks.readkeeper.uicomponent.cellview.ReadingIconToggleButton
import app.tinks.readkeeper.uicomponent.cellview.WishIconToggleButton
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@Composable
fun SearchBookListInfo(
    book: Book,
    modifier: Modifier = Modifier,
    onWishButtonClick: (Boolean) -> Unit = {},
    onReadingButtonClick: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        InfoText(text = book.basicInfo.author, maxLine = 2)
        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            RatingText(rating = book.basicInfo.rating)
            PageText(pageFormat = book.pageFormat, realPage = book.realPages)
            PubYearText(year = book.basicInfo.pubYear)
        }
        Row(
            modifier = Modifier
                .align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.End)
        ) {
            var wishChecked by remember { mutableStateOf(false) }
            var readingChecked by remember { mutableStateOf(false) }
            ReadingIconToggleButton(
                checked = readingChecked
            ) {
                readingChecked = it
                onReadingButtonClick(it)
            }
            WishIconToggleButton(checked = wishChecked) {
                wishChecked = it
                onWishButtonClick(it)
            }
        }
    }
}

@PreviewAnnotation
@Composable
private fun SearchPreview() {
    ReadKeeperTheme {
        Surface {
            SearchBookListInfo(book = BookFactory.buildSearchSample())
        }
    }
}

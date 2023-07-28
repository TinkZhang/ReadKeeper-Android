package app.tinks.readkeeper.uicomponent.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.PreviewAnnotation
import app.tinks.readkeeper.uicomponent.cellview.BookCardImageSmall
import app.tinks.readkeeper.uicomponent.theme.ReadKeeperTheme

@ExperimentalMaterial3Api
@Composable
fun BookListCard(
    book: Book,
    modifier: Modifier = Modifier,
    onAddProgressClick: () -> Unit = {},
    onWishButtonClick: (Boolean) -> Unit = {},
    onReadingButtonClick: (Boolean) -> Unit = {},
    onMoveFromWishToReadingClick: (Book) -> Unit = {},
    onSetPlatformClick: () -> Unit = {},
) {
    Card(modifier = modifier) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BookCardImageSmall(url = book.basicInfo.imageUrl, title = book.basicInfo.title)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.fillMaxHeight()) {
                        Text(
                            book.basicInfo.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            maxLines = 2,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ListBookInfo(
                            book = book,
                            onAddProgressClick = onAddProgressClick,
                            onWishButtonClick = onWishButtonClick,
                            onReadingButtonClick = onReadingButtonClick,
                            onMoveFromWishToReadingClick = onMoveFromWishToReadingClick,
                            onSetPlatformClick = onSetPlatformClick,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Divider()
                Spacer(modifier = Modifier.width(8.dp))
//                ListBookAction(book)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewAnnotation
@Composable
private fun BookListCardPreview() {
    ReadKeeperTheme {
        Surface {
            BookListCard(book = BookFactory.buildSearchSample())
        }
    }
}
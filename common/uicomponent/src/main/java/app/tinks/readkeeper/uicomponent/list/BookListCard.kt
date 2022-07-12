package app.tinks.readkeeper.uicomponent.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.uicomponent.cellview.BookCardImageSmall

@ExperimentalMaterial3Api
@Composable
fun BookListCard(
    book: Book,
    modifier: Modifier = Modifier,
    onAddProgressClicked: () -> Unit = {},
    onWishButtonClicked: (Boolean) -> Unit = {},
    onReadingButtonClicked: (Boolean) -> Unit = {},
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
                            onAddProgressClicked = onAddProgressClicked,
                            onWishButtonClicked = onWishButtonClicked,
                            onReadingButtonClicked = onReadingButtonClicked
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
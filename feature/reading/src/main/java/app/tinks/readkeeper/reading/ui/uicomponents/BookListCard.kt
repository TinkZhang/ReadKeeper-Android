package app.tinks.readkeeper.reading.ui.uicomponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import app.tinks.readkeeper.uicomponent.BookCardImageSmall
import app.tinks.readkeeper.uicomponent.BookListCard

@ExperimentalMaterial3Api
@Composable
fun BookListCard(book: Book, navController: NavController? = null) {
    BookListCard(
        left = { BookCardImageSmall(url = book.basicInfo.imageUrl, title = book.basicInfo.title) },
        right = { ReadingCardMetadata(book = book) },
        bottom = {
            if (book.platform != null) {
                ReadingCardProgressBottom(book = book) {
                    navController?.navigate("reading_item/${book.basicInfo.uuid}?open_progress_dialog=${true}")
                }
            } else {
                ReadingCardEditBottom() {
                    navController?.navigate("reading_item/${book.basicInfo.uuid}?open_edit_dialog=${true}")
                }
            }
        },
        onCardClicked = {
            navController?.navigate("reading_item/${book.basicInfo.uuid}")
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ReadingCard() {
    BookListCard(book = BookFactory.buildReadingSample())
}

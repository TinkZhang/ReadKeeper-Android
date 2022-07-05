package app.tinks.readkeeper.reading.ui.uicomponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import app.tinks.readkeeper.uicomponent.BookCardImageSmall
import app.tinks.readkeeper.uicomponent.BookListCard
import app.tinks.readkeeper.basic.model.ReadingBook
import app.tinks.readkeeper.basic.model.ReadingBookFactory

@ExperimentalMaterial3Api
@Composable
fun ReadingListCard(book: ReadingBook, navController: NavController? = null) {
    BookListCard(
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = { ReadingCardMetadata(book = book) },
        bottom = {
            if (book.formatEdited) {
                ReadingCardProgressBottom(book = book) {
                    navController?.navigate("reading_item/${book.bookInfo.uuid}?open_progress_dialog=${true}")
                }
            } else {
                ReadingCardEditBottom() {
                    navController?.navigate("reading_item/${book.bookInfo.uuid}?open_edit_dialog=${true}")
                }
            }
        },
        onCardClicked = {
            navController?.navigate("reading_item/${book.bookInfo.uuid}")
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ReadingCard() {
    ReadingListCard(book = ReadingBookFactory.buildSample())
}

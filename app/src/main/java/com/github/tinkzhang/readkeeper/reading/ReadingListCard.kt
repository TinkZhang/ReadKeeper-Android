package com.github.tinkzhang.readkeeper.reading

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.github.tinkzhang.readkeeper.model.ReadingBook
import com.github.tinkzhang.readkeeper.model.ReadingBookFactory
import com.github.tinkzhang.readkeeper.reading.uicomponents.*

@Composable
fun ReadingListCard(book: ReadingBook, navController: NavController? = null) {
    BookListCard(
        left = { BookCardImage(book = book) },
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

@Preview
@Composable
private fun ReadingCard() {
    ReadingListCard(book = ReadingBookFactory.buildSample())
}

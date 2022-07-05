package app.tinks.readkeeper.archived.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import app.tinks.readkeeper.archived.ui.components.ArchivedCardMetadata
import app.tinks.readkeeper.uicomponent.BookCardImageSmall
import app.tinks.readkeeper.uicomponent.BookListCard
import app.tinks.readkeeper.basic.model.ArchivedBook
import app.tinks.readkeeper.basic.model.ArchivedBookFactory

@ExperimentalMaterial3Api
@Composable
fun ArchivedListCard(book: ArchivedBook, navController: NavController? = null) {
    BookListCard(
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = { ArchivedCardMetadata(book = book) },
        bottom = {
        },
        onCardClicked = {
            navController?.navigate("archived_item/${book.bookInfo.uuid}")
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ArchivedCard() {
    ArchivedListCard(book = ArchivedBookFactory.buildSample())
}

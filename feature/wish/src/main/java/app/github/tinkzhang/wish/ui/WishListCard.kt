package app.github.tinkzhang.wish.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import app.github.tinkzhang.uicomponent.BookCardImageSmall
import app.github.tinkzhang.uicomponent.BookListCard
import app.github.tinkzhang.wish.ui.components.WishCardEditBottom
import app.github.tinkzhang.wish.ui.components.WishCardMetadata
import app.tinks.readkeeper.basic.model.WishBook
import app.tinks.readkeeper.basic.model.WishBookFactory


@ExperimentalMaterial3Api
@Composable
fun WishListCard(
    book: WishBook,
    navController: NavController? = null,
    onMoveToReadingClicked: () -> Unit = {}
) {
    BookListCard(
        left = { BookCardImageSmall(url = book.bookInfo.imageUrl, title = book.bookInfo.title) },
        right = { WishCardMetadata(book = book) },
        bottom = {
            WishCardEditBottom(onButtonClicked = onMoveToReadingClicked)
        },
        onCardClicked = {
            navController?.navigate("wish_item/${book.bookInfo.uuid}")
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun ReadingCard() {
    WishListCard(book = WishBookFactory.buildSample())
}
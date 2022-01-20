package com.github.tinkzhang.wish.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.github.tinkzhang.basic.model.WishBook
import com.github.tinkzhang.basic.model.WishBookFactory
import com.github.tinkzhang.uicomponent.BookCardImageSmall
import com.github.tinkzhang.uicomponent.BookListCard
import com.github.tinkzhang.wish.ui.components.WishCardEditBottom
import com.github.tinkzhang.wish.ui.components.WishCardMetadata


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

@Preview
@Composable
private fun ReadingCard() {
    WishListCard(book = WishBookFactory.buildSample())
}
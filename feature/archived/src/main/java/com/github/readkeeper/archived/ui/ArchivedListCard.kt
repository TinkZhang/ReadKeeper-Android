package com.github.readkeeper.archived.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.github.readkeeper.archived.ui.components.ArchivedCardEditBottom
import com.github.readkeeper.archived.ui.components.ArchivedCardMetadata
import com.github.tinkzhang.basic.model.ArchivedBook
import com.github.tinkzhang.basic.model.ArchivedBookFactory
import com.github.tinkzhang.uicomponent.BookCardImage
import com.github.tinkzhang.uicomponent.BookListCard

@Composable
fun ArchivedListCard(book: ArchivedBook, navController: NavController? = null) {
    BookListCard(
        left = { BookCardImage(book = book) },
        right = { ArchivedCardMetadata(book = book) },
        bottom = {
            ArchivedCardEditBottom()
        },
        onCardClicked = {
            navController?.navigate("reading_item/${book.bookInfo.uuid}")
        }
    )
}

@Preview
@Composable
private fun ArchivedCard() {
    ArchivedListCard(book = ArchivedBookFactory.buildSample())
}

package com.github.readkeeper.archived.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.github.readkeeper.archived.ui.components.ArchivedCardMetadata
import com.github.tinkzhang.basic.model.ArchivedBook
import com.github.tinkzhang.basic.model.ArchivedBookFactory
import com.github.tinkzhang.uicomponent.BookCardImageSmall
import com.github.tinkzhang.uicomponent.BookListCard

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

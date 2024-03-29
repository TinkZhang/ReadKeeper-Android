package app.tinks.readkeeper.uicomponent.detail.actionsection

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Status

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ActionSection(
    book: Book,
    modifier: Modifier = Modifier,
    onAddToReadingClick: () -> Unit = {},
    onAddToWishClick: () -> Unit = {},
    onMoveToReadingClick: () -> Unit = {},
) {
    AnimatedContent(targetState = book.status) {
        when (book.status) {
            Status.READING, Status.ARCHIVED -> Unit
            Status.WISH -> WishActionSection(
                modifier = modifier,
                onMoveToReadingClick = onMoveToReadingClick,
            )
            Status.SEARCH -> SearchActionSection(
                modifier = modifier,
                onAddToReadingClick = onAddToReadingClick,
                onAddToWishClick = onAddToWishClick
            )
        }
    }
}

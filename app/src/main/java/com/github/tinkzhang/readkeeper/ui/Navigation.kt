package com.github.tinkzhang.readkeeper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.readkeeper.R

object SCREEN_ROUTE {
    const val HOME = "home"
    const val READING_LIST = "reading_list"
    const val WISH_LIST = "wish_list"
    const val ARCHIVED_LIST = "archived_list"
    const val SEARCH = "search/{keyword}"
}

sealed class Screen(val route: String, @StringRes val labelId: Int, var icon: ImageVector) {
    object HomePage : Screen(SCREEN_ROUTE.HOME, R.string.home, Icons.Filled.Home)
    object ReadingListPage :
        Screen(SCREEN_ROUTE.READING_LIST, R.string.reading, Icons.Filled.Bookmark)

    object WishListPage : Screen(SCREEN_ROUTE.WISH_LIST, R.string.wish, Icons.Filled.Favorite)
    object ArchivedPage :
        Screen(SCREEN_ROUTE.ARCHIVED_LIST, R.string.archived, Icons.Filled.Archive)
}

fun getBottomBarItemList() = listOf(
    Screen.HomePage,
    Screen.ReadingListPage,
    Screen.WishListPage,
    Screen.ArchivedPage,
)

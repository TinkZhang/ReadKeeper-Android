package com.github.tinkzhang.readkeeper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.readkeeper.R

object SCREEN_ROUTE {
    const val HOME = "home"
    const val READING_LIST = "reading_list"
    const val WISH_LIST = "wish_list"
    const val ARCHIVED_LIST = "archived_list"
    const val SEARCH = "search/{keyword}"
    const val SETTINGS = "settings"
}

sealed class Screen(
    val route: String,
    @StringRes val labelId: Int,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector
) {
    object HomePage :
        Screen(SCREEN_ROUTE.HOME, R.string.home, Icons.Filled.Home, Icons.Outlined.Home)

    object ReadingListPage :
        Screen(
            SCREEN_ROUTE.READING_LIST,
            R.string.reading,
            Icons.Filled.Bookmark,
            Icons.Filled.BookmarkBorder
        )

    object WishListPage : Screen(
        SCREEN_ROUTE.WISH_LIST,
        R.string.wish,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder
    )

    object ArchivedPage :
        Screen(
            SCREEN_ROUTE.ARCHIVED_LIST,
            R.string.archived,
            Icons.Filled.Archive,
            Icons.Outlined.Archive
        )

    object SettingsPage : Screen(
        SCREEN_ROUTE.SETTINGS,
        R.string.settings,
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )
}

fun getBottomBarItemList() = listOf(
    Screen.HomePage,
    Screen.ReadingListPage,
    Screen.WishListPage,
    Screen.ArchivedPage,
)

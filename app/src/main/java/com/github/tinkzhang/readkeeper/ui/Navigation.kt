package com.github.tinkzhang.readkeeper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.readkeeper.R
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.ARCHIVED_LIST
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.HOME
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.READING_LIST
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.SEARCH
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.SETTINGS
import com.github.tinkzhang.readkeeper.ui.SCREEN_ROUTE.WISH_LIST

object SCREEN_ROUTE {
    const val HOME = "home"
    const val READING_LIST = "reading_list"
    const val READING_ITEM =
        "reading_item/{uuid}?open_progress_dialog={open_progress_dialog}&open_edit_dialog={open_edit_dialog}"
    const val WISH_LIST = "wish_list"
    const val ARCHIVED_LIST = "archived_list"
    const val SEARCH = "search"
    const val SEARCH_RESUTL = "search_result/{keyword}"
    const val SETTINGS = "settings"
}

val ROUTE_TO_SCREEN_MAP = mapOf(
    HOME to MainScreenViewData.HomeScreenViewData,
    READING_LIST to MainScreenViewData.ReadingListScreenViewData,
    WISH_LIST to MainScreenViewData.WishListScreenViewData,
    ARCHIVED_LIST to MainScreenViewData.ArchiveScreenViewData,
    SETTINGS to SubScreenViewData.SettingScreenViewData,
    SEARCH to SubScreenViewData.SearchScreenViewData,
)

interface ScreenViewData {
    open val route: String
}

open class SubScreenViewData(
    override val route: String,
    val title: String,
) : ScreenViewData {
    object SettingScreenViewData : SubScreenViewData(SETTINGS, "Setting")
    object SearchScreenViewData : SubScreenViewData(SEARCH, "Search")
}

sealed class MainScreenViewData(
    override val route: String,
    @StringRes val labelId: Int,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector
) : ScreenViewData {
    object HomeScreenViewData :
        MainScreenViewData(SCREEN_ROUTE.HOME, R.string.home, Icons.Filled.Home, Icons.Outlined.Home)

    object ReadingListScreenViewData :
        MainScreenViewData(
            SCREEN_ROUTE.READING_LIST,
            R.string.reading,
            Icons.Filled.Bookmark,
            Icons.Filled.BookmarkBorder
        )

    object WishListScreenViewData : MainScreenViewData(
        SCREEN_ROUTE.WISH_LIST,
        R.string.wish,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder
    )

    object ArchiveScreenViewData :
        MainScreenViewData(
            SCREEN_ROUTE.ARCHIVED_LIST,
            R.string.archived,
            Icons.Filled.Archive,
            Icons.Outlined.Archive
        )
}

fun getBottomBarItemList() = listOf(
    MainScreenViewData.HomeScreenViewData,
    MainScreenViewData.ReadingListScreenViewData,
    MainScreenViewData.WishListScreenViewData,
    MainScreenViewData.ArchiveScreenViewData,
)

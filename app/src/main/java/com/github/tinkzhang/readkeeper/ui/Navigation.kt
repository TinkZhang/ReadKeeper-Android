package com.github.tinkzhang.readkeeper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.basic.SCREEN_ROUTE
import com.github.tinkzhang.readkeeper.R

val ROUTE_TO_SCREEN_MAP = mapOf(
    SCREEN_ROUTE.HOME to MainScreenViewData.HomeScreenViewData,
    SCREEN_ROUTE.READING_LIST to MainScreenViewData.ReadingListScreenViewData,
    SCREEN_ROUTE.WISH_LIST to MainScreenViewData.WishListScreenViewData,
    SCREEN_ROUTE.ARCHIVED_LIST to MainScreenViewData.ArchiveScreenViewData,
    SCREEN_ROUTE.SETTINGS to SubScreenViewData.SettingScreenViewData,
    SCREEN_ROUTE.SEARCH to SubScreenViewData.SearchScreenViewData,
)

interface ScreenViewData {
    val route: String
}

open class SubScreenViewData(
    override val route: String,
    val title: String,
) : ScreenViewData {
    object SettingScreenViewData : SubScreenViewData(SCREEN_ROUTE.SETTINGS, "Setting")
    object SearchScreenViewData : SubScreenViewData(SCREEN_ROUTE.SEARCH, "Search")
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

package com.github.tinkzhang.readkeeper.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.github.tinkzhang.readkeeper.R
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.material.bottomnavigation.BottomNavigationItemView

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
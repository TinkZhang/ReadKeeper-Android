package com.github.tinkzhang.readkeeper.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class RkScreen(
    val icon: ImageVector,
) {
    Home(icon = Icons.Filled.Home),
    Reading(icon = Icons.Filled.Bookmark),
    Wish(icon = Icons.Filled.Favorite),
    Archived(icon = Icons.Filled.Archive),
    Search(icon = Icons.Filled.Search);

    companion object {
        fun fromRoute(route: String?): RkScreen =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                Reading.name -> Reading
                Wish.name -> Wish
                Archived.name -> Archived
                Search.name -> Search
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}

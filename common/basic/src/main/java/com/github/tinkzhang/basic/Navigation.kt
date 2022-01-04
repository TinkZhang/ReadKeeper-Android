package com.github.tinkzhang.basic

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
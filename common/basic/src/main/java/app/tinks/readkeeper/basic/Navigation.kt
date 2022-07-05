package app.tinks.readkeeper.basic

object SCREEN_ROUTE {
    const val HOME = "home"
    const val WEEKLY_ITEM = "weekly_item/{title}?type={type}"
    const val READING_LIST = "reading_list"
    const val READING_ITEM =
        "reading_item/{uuid}?open_progress_dialog={open_progress_dialog}&open_edit_dialog={open_edit_dialog}"
    const val WISH_LIST = "wish_list"
    const val WISH_ITEM = "wish_item/{uuid}"
    const val ARCHIVED_LIST = "archived_list"
    const val ARCHIVED_ITEM = "archived_item/{uuid}"
    const val SEARCH = "search"
    const val SEARCH_RESUTL = "search_result/{keyword}"
    const val SETTINGS = "settings"
}
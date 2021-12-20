package com.github.tinkzhang.readkeeper.common.data

import com.google.firebase.Timestamp

data class BookInfo(
    var title: String = "",
    var imageUrl: String = "",
    var author: String = "",
    var pages: Int = 0,
    var rating: Double = 0.0,
    var pubYear: Int = 0
)

data class TimeInfo(
    val addedTime: Timestamp = Timestamp.now(),
    val editedTime: Timestamp = Timestamp.now()
)

interface Book {
    val bookInfo: BookInfo
}

interface EditableBook : Book {
    val uuid: String
    val category: String?
    val timeInfo: TimeInfo
}

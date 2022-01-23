package com.github.tinkzhang.basic.model

import com.google.firebase.Timestamp
import java.util.*

data class BookInfo(
    val uuid: String = UUID.randomUUID().toString(),
    var title: String = "",
    var imageUrl: String = "",
    var author: String = "",
    var pages: Int = 0,
    var rating: Double = 0.0,
    var pubYear: Int = 0,
    val amazonLink: String? = null,
)

data class TimeInfo(
    val addedTime: Timestamp = Timestamp.now(),
    val editedTime: Timestamp = Timestamp.now()
)

interface Book {
    val bookInfo: BookInfo
}

interface EditableBook : Book {
    val category: String?
    val timeInfo: TimeInfo
}

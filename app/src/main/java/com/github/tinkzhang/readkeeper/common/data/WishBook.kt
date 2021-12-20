package com.github.tinkzhang.readkeeper.common.data

import java.util.*

data class WishBook(
    override val uuid: String = UUID.randomUUID().toString(),
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo()
) : EditableBook {
    fun convertToReadingBook() =
        ReadingBook(bookInfo = bookInfo, category = category)
}
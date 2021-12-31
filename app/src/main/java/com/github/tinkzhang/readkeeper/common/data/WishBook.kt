package com.github.tinkzhang.readkeeper.common.data

data class WishBook(
    override val bookInfo: BookInfo = BookInfo(),
    override val category: String? = null,
    override val timeInfo: TimeInfo = TimeInfo()
) : EditableBook {
    fun convertToReadingBook() =
        ReadingBook(bookInfo = bookInfo, category = category)
}
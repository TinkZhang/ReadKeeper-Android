package com.github.tinkzhang.readkeeper.common.data

data class SearchBook(override val bookInfo: BookInfo) : Book {
    fun convertToWishBook() = WishBook(bookInfo = bookInfo)
    fun convertToReadingBook() = ReadingBook(bookInfo = bookInfo)
}

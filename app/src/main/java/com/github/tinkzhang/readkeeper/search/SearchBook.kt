package com.github.tinkzhang.readkeeper.search

import com.github.tinkzhang.readkeeper.common.BasicBook

data class SearchBook(
    override var title: String = "",
    override var imageUrl: String = "",
    override var author: String = "",
    override var pages: Int = 0,
    override var addedTime: Long = 0,
    override var rating: Double = 0.0,
    var ratingsCount: Int = 0,
    override var originalPublicationYear: Int = 1900
) : BasicBook()

fun SearchBook.buildSample(): SearchBook {
    return SearchBook(
        title = "Hello World",
        imageUrl = "http://books.google.com/books/content?id=gK98gXR8onwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
        author = "Tink",
        pages = 1688,
        addedTime = 2019,
        rating = 5.0,
        ratingsCount = 999,
        originalPublicationYear = 1987
    )
}
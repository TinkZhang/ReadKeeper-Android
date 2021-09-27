package com.github.tinkzhang.readkeeper.common

import com.github.tinkzhang.readkeeper.reading.ReadingBook
import com.github.tinkzhang.readkeeper.search.SearchBook
import com.github.tinkzhang.readkeeper.wish.WishBook
import com.google.firebase.Timestamp

fun  SearchBook.convertToReadingBook() = ReadingBook(
        title = title,
        imageUrl = imageUrl,
        author = author,
        pages = pages,
        addedTime = Timestamp.now(),
        rating = rating,
        originalPublicationYear = originalPublicationYear,
    )

fun  SearchBook.convertToWishBook() = WishBook(
        title = title,
        imageUrl = imageUrl,
        author = author,
        pages = pages,
        addedTime = Timestamp.now(),
        rating = rating,
        originalPublicationYear = originalPublicationYear,
)

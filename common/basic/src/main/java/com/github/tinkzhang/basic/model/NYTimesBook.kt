package com.github.tinkzhang.basic.model

data class NYTimesBook(
    val title: String = "",
    val author: String = "",
    val bookImage: String = "",
    val amazonProductUrl: String? = null,
    val description: String? = null,
    val rank: Int = 0,
    val rankLastWeek: Int = 0,
    val weeksOnList: Int = 0,
) {
    fun toWish(): WishBook = WishBook(
        bookInfo = BookInfo(
            title = title,
            imageUrl = bookImage,
            author = author,
        )
    )
}

fun NYTimesBookSample() = NYTimesBook(
    title = "INVISIBLE",
    author = "Danielle Steel",
    bookImage = "https://images-na.ssl-images-amazon.com/images/I/416Uc0RhQWL._SX327_BO1,204,203,200_.jpg",
    amazonProductUrl = "https://www.amazon.com/dp/198482158X?tag=NYTBSREV-20",
    description = "The daughter of a couple in a loveless marriage is discovered by a British filmmaker and thrust into the public eye.",
    rank = 1,
    rankLastWeek = 0,
    weeksOnList = 1,
)

enum class NYBookType {
    Fictions,
    NonFictions
}
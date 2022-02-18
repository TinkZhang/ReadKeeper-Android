package com.github.tinkzhang.basic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nytimesbook")
data class NYTimesBook(
    @PrimaryKey
    val title: String = "",
    @ColumnInfo
    val author: String = "",
    @ColumnInfo
    val bookImage: String = "",
    @ColumnInfo
    val amazonProductUrl: String? = null,
    @ColumnInfo
    val description: String? = null,
    @ColumnInfo
    val rank: Int = 0,
    @ColumnInfo
    val rankLastWeek: Int = 0,
    @ColumnInfo
    val weeksOnList: Int = 0,
    @ColumnInfo
    val type: NYBookType = NYBookType.Fictions
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
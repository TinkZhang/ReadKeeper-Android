package com.github.tinkzhang.basic.model

data class NYTimesBook(
    val title: String,
    val author: String,
    val bookImage: String?,
    val amazonProductUrl: String?,
    val description: String?,
    val rank: Int,
    val rankLastWeek: Int,
    val weeksOnList: Int,
)

enum class NYBookType {
    Fiction,
    NonFiction
}
package com.github.tinkzhang.search.network.googlebook

import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("averageRating")
    val averageRating: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?,
    @SerializedName("industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifier>?,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("publishedDate")
    val publishedDate: String?,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("ratingsCount")
    val ratingsCount: Int,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String
)

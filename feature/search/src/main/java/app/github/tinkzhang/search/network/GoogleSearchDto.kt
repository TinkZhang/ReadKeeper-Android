package app.github.tinkzhang.search.network

import app.github.tinkzhang.search.network.googlebook.GoogleBookItem
import com.google.gson.annotations.SerializedName

data class GoogleBookDto(
    @SerializedName("items")
    val googleBookItems: List<GoogleBookItem>,
    @SerializedName("totalItems")
    val totalItems: Int
)
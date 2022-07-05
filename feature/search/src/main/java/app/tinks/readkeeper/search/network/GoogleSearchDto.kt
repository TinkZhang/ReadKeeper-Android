package app.tinks.readkeeper.search.network

import app.tinks.readkeeper.search.network.googlebook.GoogleBookItem
import com.google.gson.annotations.SerializedName

data class GoogleBookDto(
    @SerializedName("items")
    val googleBookItems: List<GoogleBookItem>,
    @SerializedName("totalItems")
    val totalItems: Int
)

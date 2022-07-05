package app.github.tinkzhang.search.network.googlebook

import com.google.gson.annotations.SerializedName

data class GoogleBookItem(
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

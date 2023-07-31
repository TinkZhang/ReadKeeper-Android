package app.tinks.readkeeper.search.network.googlebook

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImageLinks(
    @SerializedName("smallThumbnail")
    val smallThumbnail: String,
    @SerializedName("thumbnail")
    val thumbnail: String?
)

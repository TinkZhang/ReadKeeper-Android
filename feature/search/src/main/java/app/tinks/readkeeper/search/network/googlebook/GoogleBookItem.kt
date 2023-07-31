package app.tinks.readkeeper.search.network.googlebook

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GoogleBookItem(
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo
)

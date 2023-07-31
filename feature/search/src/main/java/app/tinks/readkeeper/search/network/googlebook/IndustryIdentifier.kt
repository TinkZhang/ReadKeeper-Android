package app.tinks.readkeeper.search.network.googlebook

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class IndustryIdentifier(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("type")
    val type: String
)

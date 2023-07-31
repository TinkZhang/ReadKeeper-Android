package app.tinks.readkeeper.firebaseRemoteConfig

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchEngine(
    @SerializedName("name")
    val name: String,
    @SerializedName("link")
    val link: String
)

@Keep
data class SearchEngines(
    @SerializedName("searchEngines")
    val searchEngines: List<SearchEngine>
)


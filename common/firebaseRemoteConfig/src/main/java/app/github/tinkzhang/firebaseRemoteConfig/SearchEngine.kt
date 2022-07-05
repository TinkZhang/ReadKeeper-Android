package app.github.tinkzhang.firebaseRemoteConfig

import com.google.gson.annotations.SerializedName

data class SearchEngine(
    @SerializedName("name")
    val name: String,
    @SerializedName("link")
    val link: String
)

data class SearchEngines(
    @SerializedName("searchEngines")
    val searchEngines: List<SearchEngine>
)


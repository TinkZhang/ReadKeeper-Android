package com.github.tinkzhang.firebaseRemoteConfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson

const val KeyIsWishVipSearchLinkEnabled = "isWishVipSearchLinkEnabled"
const val KeyJsonSearchEngines = "jsonSearchEngines"

object FirebaseRemoteConfigWrapper {
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    init {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(remoteConfigMap)
        }
    }

    val isWishVipSearchLinkEnabled = Firebase.remoteConfig.getBoolean(KeyIsWishVipSearchLinkEnabled)
    val searchEngines: SearchEngines? = Gson().fromJson(
        Firebase.remoteConfig.getString(KeyJsonSearchEngines),
        SearchEngines::class.java
    )

}

private val remoteConfigMap: Map<String, Any> = mapOf(
    KeyIsWishVipSearchLinkEnabled to true,
    KeyJsonSearchEngines to "{\"searchEngines\": [{\"name\": \"Hello\", \"link\": \"www.google.com\"},{\"name\": \"Hello\", \"link\": \"www.google.com\"}]}"
)

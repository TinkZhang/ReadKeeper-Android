package app.tinks.readkeeper.firebaseRemoteConfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson

const val KeyIsDetailPageSearchLinkEnabled = "bDetailPageSearchLinkEnabled"
const val KeyIsDetailPageBannerEnabled = "bDetailPageBannerEnabled"
const val KeyJsonSearchEngines = "jsonSearchEngines"

object FirebaseRemoteConfigWrapper {
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    init {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(remoteConfigMap)
            fetchAndActivate()
        }
    }

    val isDetailPageSearchLinkEnabled =
        Firebase.remoteConfig.getBoolean(KeyIsDetailPageSearchLinkEnabled)
    val isDetailPagBannerEnabled = Firebase.remoteConfig.getBoolean(KeyIsDetailPageBannerEnabled)
    val searchEngines: SearchEngines? = try {
        Gson().fromJson(
            Firebase.remoteConfig.getString(KeyJsonSearchEngines),
            SearchEngines::class.java
        )
    } catch (e: Exception) {
        null
    }

}

private val remoteConfigMap: Map<String, Any> = mapOf(
    KeyIsDetailPageSearchLinkEnabled to false,
    KeyIsDetailPageBannerEnabled to false,
    KeyJsonSearchEngines to "{\"searchEngines\": [{{\"name\": \"Google\", \"link\": \"https://www.google.com/search?q=KEYWORD&tbm=bks\"}]}"
)

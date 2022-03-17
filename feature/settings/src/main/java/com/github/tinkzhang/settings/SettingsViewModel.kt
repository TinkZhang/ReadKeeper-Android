package com.github.tinkzhang.settings

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.DataStoreRepository
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.settings.model.SettingItem
import com.github.tinkzhang.settings.model.SettingSummaryItem
import com.github.tinkzhang.settings.model.SettingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val userRepository: UserRepository
) : ViewModel() {
    var themeStatus: Flow<ThemeStatus> = dataStoreRepository.getString("theme").map {
        Log.d("Settings", it.orEmpty())
        ThemeStatus.valueOf(it ?: ThemeStatus.DEFAULT.name)
    }

    fun getSettingItem(key: String): SettingItem? {
        val settingItem: SettingItem? = null
        for (settingSummaryItem in settingSummaryItems) {
            val temp = settingSummaryItem.items.firstOrNull { it.key == key }
            if (temp != null) {
                return temp
            }
        }
        return null
    }

    suspend fun saveSetting(key: String, value: String) {
        Log.d("", "Update $key with $value")
        dataStoreRepository.updateString(key, value)
    }

    var displayedDialogKey: String by mutableStateOf("")



    val settingSummaryItems = mutableStateListOf(
        SettingSummaryItem(
            "General",
            items = listOf(
                SettingItem(
                    title = "Theme",
                    key = "theme",
                    icon = Icons.Default.DarkMode,
                    type = SettingType.SingleSelection(
                        "System default",
                        ThemeStatus.values().map { it.label }
                    )
                ),
                SettingItem(
                    title = "Language",
                    key = "language",
                    icon = Icons.Default.Language,
                    type = SettingType.SingleSelection(
                        "System default",
                        listOf("System default", "Dark", "Light")
                    )
                )
            )
        ),
        SettingSummaryItem("Feedback", items = listOf()),
        SettingSummaryItem("About", items = listOf())
    )
}

enum class ThemeStatus(val label: String) {
    DARK("Dark"), LIGHT("Light"), DEFAULT("System default")
}

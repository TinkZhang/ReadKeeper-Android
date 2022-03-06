package com.github.tinkzhang.settings

import androidx.lifecycle.ViewModel
import com.github.tinkzhang.basic.DataStoreRepository
import com.github.tinkzhang.basic.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    val userRepository: UserRepository
) : ViewModel() {
    fun toggleExpandedState(title: String) {
        test.first{ it.title == title }.apply {
            isExpanded = !isExpanded
        }
    }

    val items: List<SettingItem> = listOf(
        SettingItem("General", "Theme, language & notifications"),
        SettingItem("Feedback", "Report issues and improvements"),
        SettingItem("About", "Build version, Terms of Service")
    )
    val test: MutableList<SettingItem> = mutableListOf(
        SettingItem("General", "Theme, language & notifications"),
        SettingItem("Feedback", "Report issues and improvements"),
        SettingItem("About", "Build version, Terms of Service")
    )
}

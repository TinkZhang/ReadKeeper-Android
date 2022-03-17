package com.github.tinkzhang.settings.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.basic.mockStringMedium
import com.github.tinkzhang.basic.mockStringShort

data class SettingItem(
    val title: String,
    val key: String = title,
    val type: SettingType,
    val icon: ImageVector,
    val description: String = if (type is SettingType.SingleSelection) {
        type.value
    } else "",
    val rightContent: @Composable () -> Unit = {}
)

fun mockSettingItem(type: SettingType) = SettingItem(
    title = mockStringShort,
    type = type,
    icon = Icons.Default.Settings,
    description = mockStringMedium
)

sealed class SettingType {
    data class SingleSelection(val value: String, val options: List<String>) : SettingType()
    data class MultipleSelection(val values: String, val options: List<String>) : SettingType()
    data class Toggle(val value: Boolean) : SettingType()
    data class WebLink(val url: String) : SettingType()
    data class NewPage(val destination: String) : SettingType()
}


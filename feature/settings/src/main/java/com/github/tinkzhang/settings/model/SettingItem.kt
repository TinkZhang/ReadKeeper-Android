package com.github.tinkzhang.settings.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.tinkzhang.basic.mockStringShort

class SingleSelectionItem(
    override val commonAttribute: SettingAttribute,
    val options: List<String>
) : SettingItem()

data class StaticItem(
    override val commonAttribute: SettingAttribute,
): SettingItem()

data class OpenPageItem(
    override val commonAttribute: SettingAttribute,
): SettingItem()

data class SettingAttribute(
    val title: String,
    val key: String = title,
    val icon: ImageVector,
)

fun mockSettingItem() = SingleSelectionItem(
    commonAttribute = SettingAttribute(
        title = mockStringShort,
        icon = Icons.Default.Settings,
    ),
    options = listOf("Hello", "World", "Peace")
)

abstract class SettingItem {
    abstract val commonAttribute: SettingAttribute
}


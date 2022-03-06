package com.github.tinkzhang.settings

data class SettingItem(
    val title: String,
    val subtitle: String,
    var isExpanded: Boolean = false,
)

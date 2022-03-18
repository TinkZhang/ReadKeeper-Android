package com.github.tinkzhang.settings.model

import com.github.tinkzhang.basic.mockStringShort

data class SettingSummaryItem(
    val title: String,
    val items: List<SettingItem>
)

fun mockSettingSummaryItem() = SettingSummaryItem(
    title = mockStringShort,
    items = listOf(mockSettingItem(), mockSettingItem())
)

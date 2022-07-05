package app.tinks.readkeeper.settings.model

import app.tinks.readkeeper.basic.mockStringShort

data class SettingSummaryItem(
    val title: String,
    val items: List<SettingItem>
)

fun mockSettingSummaryItem() = SettingSummaryItem(
    title = mockStringShort,
    items = listOf(mockSettingItem(), mockSettingItem())
)

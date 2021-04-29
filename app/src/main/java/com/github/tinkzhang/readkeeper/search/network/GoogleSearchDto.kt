package com.github.tinkzhang.readkeeper.search.network

import github.tinkzhang.readkeeper.search.model.googlebook.Item

data class GoogleBookDto(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)

package github.tinkzhang.readkeeper.search.model.googlebook

import com.github.tinkzhang.search.network.googlebook.Item

data class GoogleBookDto(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)

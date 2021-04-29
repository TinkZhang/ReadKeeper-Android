package github.tinkzhang.readkeeper.search.model.googlebook

data class GoogleBookDto(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)
package com.github.tinkzhang.readkeeper.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.tinkzhang.readkeeper.search.network.SIZE
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import kotlinx.coroutines.flow.map

class SearchViewModel(private val keyword: String) : ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = SIZE)
    ) {
        SearchResultDataSource(keyword)
    }.flow.map { pagingData -> pagingData.map { it.convertToSearchBook() } }
        .cachedIn(viewModelScope)

    fun addHistory(keyword: String) {
        // TODO: add search history
    }
}

private fun Item.convertToSearchBook(): SearchBook {
    return SearchBook(
        title = this.volumeInfo.title,
        imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: "",
        author = this.volumeInfo.authors?.joinToString() ?: "",
        rating = this.volumeInfo.averageRating.toDouble(),
        ratingsCount = this.volumeInfo.ratingsCount,
        originalPublicationYear = this.volumeInfo.publishedDate?.split('-')?.first()?.toInt() ?: 0
    )
}

class SearchViewModelFactory(private val keyword: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(keyword) as T
    }
}

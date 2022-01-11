package com.github.tinkzhang.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.BookInfo
import com.github.tinkzhang.basic.model.ReadingBook
import com.github.tinkzhang.basic.model.SearchBook
import com.github.tinkzhang.basic.model.WishBook
import com.github.tinkzhang.search.network.SIZE
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import kotlinx.coroutines.flow.map

class SearchResultViewModel(private val keyword: String) : ViewModel() {
    val flow = Pager(
        PagingConfig(pageSize = SIZE)
    ) {
        SearchResultDataSource(keyword)
    }.flow.map { pagingData -> pagingData.map { it.convertToSearchBook() } }
        .cachedIn(viewModelScope)

    fun addWish(book: WishBook) {
        UserRepository.addBook(book)
    }

    fun removeWish(uuid: String) {
        UserRepository.removeWishBook(uuid)
    }

    fun addReading(book: ReadingBook) {
        UserRepository.addBook(book)
    }

    fun removeReading(uuid: String) {
        UserRepository.removeReadingBook(uuid)
    }
}

private fun Item.convertToSearchBook(): SearchBook {
    return SearchBook(
        bookInfo = BookInfo(
            title = this.volumeInfo.title,
            imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: "",
            author = this.volumeInfo.authors?.joinToString() ?: "",
            rating = this.volumeInfo.averageRating,
            pages = this.volumeInfo.pageCount ?: 999,
            pubYear = this.volumeInfo.publishedDate?.split('-')?.first()?.toInt() ?: 0
        )
    )
}

class SearchResultViewModelFactory(private val keyword: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchResultViewModel(keyword) as T
    }
}

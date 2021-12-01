package com.github.tinkzhang.readkeeper.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.github.tinkzhang.readkeeper.search.network.SIZE
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import kotlinx.coroutines.flow.map

class SearchViewModel : ViewModel() {
    var searchKeyword = MutableLiveData<String>()

    val flow = Pager(
        PagingConfig(pageSize = SIZE)
    ) {
        SearchResultDataSource(searchKeyword.value ?: "")
    }.flow.map { pagingData -> pagingData.map { it.convertToSearchBook() } }
        .cachedIn(viewModelScope)


    fun searchBook(keyword: String) {
//        viewModelScope.launch {
//            try {
//                isLoading.value = true
//                searchKeyword.value = keyword
//                val data = withContext(Dispatchers.IO) {
//                    GoogleBookService.instance.search(keyword)
//                }
//                if (data.code() == 200) {
//                    Timber.d(data.body()?.totalItems.toString())
//                    books.value = data.body()?.items?.map { it.convertToSearchBook() }
//                    isLoading.value = false
//                }
//                Timber.d(data.toString())
//            } catch (e: Exception) {
//                Timber.e(e.toString())
//                isLoading.value = false
//            }
//        }
    }

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

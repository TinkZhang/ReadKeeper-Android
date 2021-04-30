package com.github.tinkzhang.readkeeper.search

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tinkzhang.readkeeper.search.network.GoogleBookService
import github.tinkzhang.readkeeper.search.model.googlebook.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchViewModel() : ViewModel() {
    var books = MutableLiveData<List<SearchBook>>()
    var isLoading = MutableLiveData(false)

    fun searchBook(keyword: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val data = withContext(Dispatchers.IO) {
                    GoogleBookService.instance.search(keyword)
                }
                if (data.code() == 200) {
                    Timber.d(data.body()?.totalItems.toString())
                    books.value = data.body()?.items?.map { it.convertToSearchBook() }
                    isLoading.value = false
                }
                Timber.d(data.toString())
            } catch (e: Exception) {
                Timber.e(e.toString())
                isLoading.value = false
            }
        }
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
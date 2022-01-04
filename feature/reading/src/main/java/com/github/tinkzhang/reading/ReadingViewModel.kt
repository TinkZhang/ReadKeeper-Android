package com.github.tinkzhang.reading

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.github.tinkzhang.basic.model.ReadingBook
import com.github.tinkzhang.basic.model.ReadingBookFactory
import com.github.tinkzhang.basic.UserRepository

class ReadingViewModel(
) : ViewModel() {

    val isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
     var categoryScrollPosition: LazyListState = LazyListState()

    val selectedCategory: MutableState<String?> = mutableStateOf(null)

    private val _list = MutableLiveData(mutableListOf<ReadingBook>())
    val list: LiveData<MutableList<ReadingBook>> = _list

    val localList = mutableSetOf<ReadingBook>()

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf("Coding", "Life", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"))
    }

    val flow = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        ReadingDataSource(UserRepository)
    }.flow.cachedIn(viewModelScope)

    fun addBook(book: ReadingBook = ReadingBookFactory.buildSample()) {
        UserRepository.addReadingBook(book)
    }

    fun addLocalList(book: ReadingBook) {
        localList.add(book)
    }

    fun getBook(uuid: String) = localList.first { it.bookInfo.uuid == uuid }

    fun newSearch() {

    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = category
    }

    fun onChangeCategoryScrollPosition(position: LazyListState){
        categoryScrollPosition = position
    }

    fun resetLocalList() {
        localList.clear()
    }

    fun updateLocalList(book: ReadingBook) {
        localList.removeIf { it.bookInfo.uuid == book.bookInfo.uuid }
        localList.add(book)
    }

}
package com.github.tinkzhang.readkeeper.reading

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
import com.github.tinkzhang.readkeeper.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
     var categoryScrollPosition: LazyListState = LazyListState()

    val selectedCategory: MutableState<String?> = mutableStateOf(null)

    private val _list = MutableLiveData(mutableListOf<ReadingBook>())
    val list: LiveData<MutableList<ReadingBook>> = _list

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf("Coding", "Life", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"))
    }

    val flow = Pager(
        PagingConfig(pageSize = PAGE_SIZE)
    ) {
        ReadingDataSource(userRepository)
    }.flow.cachedIn(viewModelScope)

    fun addBook(book: ReadingBook = ReadingBookFactory.buildSample()) {
        userRepository.addReadingBook(book)
    }

    fun newSearch() {

    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = category
    }

    fun onChangeCategoryScrollPosition(position: LazyListState){
        categoryScrollPosition = position
    }

}

package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.tinkzhang.readkeeper.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
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
        MutableLiveData<List<String>>(listOf("Coding", "Lifing", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"))
    }

    init {
        syncList()
    }

    fun addBook(book: ReadingBook = ReadingBookFactory.buildSample()) {
        userRepository.addReadingBook(book)
        syncList()
    }

    fun syncList() {
        _list.value = userRepository.getReadingList()
        Timber.d("_list size: ${_list.value?.size}")
        Timber.d("list size: ${list.value?.size}")
        Timber.d("${userRepository.getReadingList()?.size}")
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

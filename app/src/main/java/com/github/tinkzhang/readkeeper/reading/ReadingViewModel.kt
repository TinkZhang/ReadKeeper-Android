package com.github.tinkzhang.readkeeper.reading

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    val list: MutableLiveData<MutableList<ReadingBook>> by lazy {
        MutableLiveData<MutableList<ReadingBook>>()
    }

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf("Coding", "Lifing", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"))
    }

    init {
        syncList()
    }

    fun addBook(book: ReadingBook = ReadingBookFactory.buildSample()) {
        userRepository.addReadingBook(book)
    }

    fun syncList() {
        list.value = userRepository.getReadingList()
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

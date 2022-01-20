package com.github.tinkzhang.homepage.weeklybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.basic.model.NYTimesBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeeklyBookViewModel : ViewModel() {

    private val _fictionBooks = MutableStateFlow<List<NYTimesBook>>(listOf())
    val fictionBooks: StateFlow<List<NYTimesBook>> = _fictionBooks

    private val _nonFictionBooks = MutableStateFlow<List<NYTimesBook>>(listOf())
    val nonFictionBooks: StateFlow<List<NYTimesBook>> = _nonFictionBooks

    init {
        viewModelScope.launch {
            _fictionBooks.value = UserRepository.getWeeklyBooks(NYBookType.Fictions)
            _nonFictionBooks.value = UserRepository.getWeeklyBooks(NYBookType.NonFictions)
        }
    }

    fun getBook(title: String, type: NYBookType): NYTimesBook = when (type) {
        NYBookType.NonFictions -> _nonFictionBooks.value.first { it.title == title }
        NYBookType.Fictions -> _fictionBooks.value.first { it.title == title }
    }

}
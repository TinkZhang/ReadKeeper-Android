package com.github.tinkzhang.homepage.weeklybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.basic.model.NYTimesBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyBookViewModel @Inject constructor(
    private val repository: WeeklyBookRepository
) : ViewModel() {
    private val _fictionBooks = MutableStateFlow<List<NYTimesBook>>(listOf())
    val fictionBooks: StateFlow<List<NYTimesBook>> = _fictionBooks

    private val _nonFictionBooks = MutableStateFlow<List<NYTimesBook>>(listOf())
    val nonFictionBooks: StateFlow<List<NYTimesBook>> = _nonFictionBooks

    init {
        viewModelScope.launch {
            repository.fetchFictionTimeFlow.collect { value ->
                repository.getWeeklyBooks(value, NYBookType.Fictions).map { it.data!! }.collect {
                    _fictionBooks.value = it
                }
            }
        }
        viewModelScope.launch {
            repository.fetchNonFictionTimeFlow.collect { value ->
                repository.getWeeklyBooks(value, NYBookType.NonFictions).map { it.data!! }.collect {
                    _nonFictionBooks.value = it
                }
            }
        }
    }

    fun getBook(title: String) = UserRepository.findWeeklyBook(title)
    fun addToWish(book: NYTimesBook) {
        UserRepository.addBook(book.toWish())
    }
}
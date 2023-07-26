package app.tinks.readkeeper.homepage.weeklybook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tinks.readkeeper.basic.BookRepository
import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.convertors.convertToRecord
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomepageViewModel : ViewModel() {
    private val repository = WeeklyBookRepository
    private val bookRepository = BookRepository
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

    fun getAllRecords(): Flow<List<Record>> =
        bookRepository.getAllRecords()
            .map { it.map { recordEntity -> recordEntity.convertToRecord() } }

    fun getBook(title: String) =
        _fictionBooks.value.firstOrNull { it.title == title }
            ?: _nonFictionBooks.value.first {
                it.title == title
            }


    fun getFirstReading() =
        bookRepository.getFirstReading().map { list -> list.map { it.convertToBook() } }
}
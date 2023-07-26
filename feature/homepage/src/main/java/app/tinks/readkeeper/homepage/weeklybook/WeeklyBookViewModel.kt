package app.tinks.readkeeper.homepage.weeklybook

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tinks.readkeeper.basic.BookRepository
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.Status
import kotlinx.coroutines.launch

class WeeklyBookViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val repository = WeeklyBookRepository
    private val bookRepository = BookRepository
    private val title: String? = savedStateHandle["title"]
    private val type: NYBookType? = savedStateHandle["type"]
    val book = repository.findWeeklyBook(title ?: "")

    fun addTo(book: NYTimesBook, status: Status) {
        viewModelScope.launch {
            bookRepository.addBook(book.convertToBook().copy(status = status))
        }
    }
}
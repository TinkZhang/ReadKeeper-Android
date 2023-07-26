package app.tinks.readkeeper.basic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.BookFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BookEditViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = BookRepository

    var uuid by mutableStateOf("")
    var bookFlow: Flow<Book>
    var book: Book = BookFactory.buildEmptyBook()

    init {
        uuid = savedStateHandle.get<String>("uuid").orEmpty()
        bookFlow = repository.getBook(uuid).map { it.first().convertToBook() }
        viewModelScope.launch {
            bookFlow.collect() {
                book = it
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            repository.update(
                book,
            )
        }
    }
}

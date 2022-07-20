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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookEditViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
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

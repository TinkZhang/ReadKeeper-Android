package app.tinks.readkeeper.basic

import androidx.lifecycle.ViewModel
import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BookEditViewModel@Inject constructor(
    private val repository: BookRepository,
    private val database: BookDatabase
) : ViewModel() {

    fun getBook(uuid: String): Flow<Book> {
        return repository.getBook(uuid).map { it.first().convertToBook() }
    }

}
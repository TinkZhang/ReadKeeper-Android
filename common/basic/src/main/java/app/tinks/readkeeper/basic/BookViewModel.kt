package app.tinks.readkeeper.basic

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import app.tinks.readkeeper.basic.convertors.convertToBook
import app.tinks.readkeeper.basic.convertors.convertToRecord
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Record
import app.tinks.readkeeper.basic.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val database: BookDatabase
) : ViewModel() {
    init {
        sync()
    }

    fun sync() {
        viewModelScope.launch {
            repository.sync()
        }
    }

    private val readingFlow = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
    ) {
        database.bookDao().pagingSource(Status.READING)
    }.flow.map { pagingData -> pagingData.map { it.convertToBook() } }.cachedIn(viewModelScope)

    private val wishFlow = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
    ) {
        database.bookDao().pagingSource(Status.WISH)
    }.flow.map { pagingData -> pagingData.map { it.convertToBook() } }.cachedIn(viewModelScope)

    private val archivedFlow = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
    ) {
        database.bookDao().pagingSource(Status.ARCHIVED)
    }.flow.map { pagingData -> pagingData.map { it.convertToBook() } }.cachedIn(viewModelScope)

    fun getListFlow(status: Status) = when (status) {
        Status.READING -> readingFlow
        Status.WISH -> wishFlow
        Status.ARCHIVED -> archivedFlow
        Status.SEARCH -> TODO()
    }

    val selectedCategory: MutableState<String?> = mutableStateOf(null)

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(
            listOf(
                "Coding", "Life", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"
            )
        )
    }

    fun getBook(uuid: String): Flow<Book> {
        return repository.getBook(uuid).map { it.first().convertToBook() }
    }

    fun getRecords(uuid: String): Flow<List<Record>> =
        repository.getRecord(uuid).map { it.map { recordEntity -> recordEntity.convertToRecord() } }

    fun add(book: Book) {
        viewModelScope.launch {
            repository.addBook(book)
        }
    }

    fun delete(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book.basicInfo.uuid)
        }
    }

    fun move(book: Book, status: Status) {
        viewModelScope.launch {
            repository.move(book, status)
        }
    }

    fun add(record: Record) {
        viewModelScope.launch {
            repository.addRecord(record)
        }
    }

    fun delete(record: Record?) {
        val id = record?.id
        if (id.isNullOrEmpty()) return
        viewModelScope.launch {
            repository.deleteRecord(id)
        }
    }

    fun updateRecord(record: Record) {
        viewModelScope.launch {
            repository.updateRecord(record)
        }
    }

}

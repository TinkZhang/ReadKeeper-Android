package app.tinks.readkeeper.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import app.tinks.readkeeper.basic.BookRepository
import app.tinks.readkeeper.basic.model.BasicInfo
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.Status
import app.tinks.readkeeper.search.network.SIZE
import app.tinks.readkeeper.search.network.googlebook.GoogleBookItem
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

const val KEYWORD = "keyword"

class SearchResultViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val bookRepository = BookRepository
    val keyword: String = savedStateHandle[KEYWORD] ?: ""

    init {
        Timber.d("Create Search Result ViewModel for keyword $keyword")
    }

    val flow = Pager(
        PagingConfig(pageSize = SIZE)
    ) {
        SearchResultDataSource(keyword)
    }.flow.map { pagingData -> pagingData.map { it.convertToBook() } }
        .cachedIn(viewModelScope)

    fun add(book: Book) {
        viewModelScope.launch {
            bookRepository.addBook(book)
        }
    }

    fun remove(uuid: String) {
        viewModelScope.launch { bookRepository.deleteBook(uuid) }
    }
}

private fun GoogleBookItem.convertToBook(): Book {
    return Book(
        basicInfo = BasicInfo(
            isbn = this.volumeInfo.industryIdentifiers?.lastOrNull()?.identifier ?: "",
            title = this.volumeInfo.title ?: "",
            imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: "",
            author = this.volumeInfo.authors?.joinToString() ?: "",
            rating = this.volumeInfo.averageRating,
            pages = this.volumeInfo.pageCount,
            pubYear = this.volumeInfo.publishedDate?.split('-')?.first()?.toIntOrNull() ?: 0
        ),
        status = Status.SEARCH
    )
}

package app.tinks.readkeeper.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import app.tinks.readkeeper.search.network.SIZE
import app.tinks.readkeeper.search.network.googlebook.GoogleBookItem
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.BookInfo
import app.tinks.readkeeper.basic.model.ReadingBook
import app.tinks.readkeeper.basic.model.SearchBook
import app.tinks.readkeeper.basic.model.WishBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

const val KEYWORD = "keyword"

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val keyword: String = savedStateHandle[KEYWORD] ?: ""

    init {
        Timber.d("Create Search Result ViewModel for keyword $keyword")
    }

    val flow = Pager(
        PagingConfig(pageSize = SIZE)
    ) {
        SearchResultDataSource(keyword)
    }.flow.map { pagingData -> pagingData.map { it.convertToSearchBook() } }
        .cachedIn(viewModelScope)

    fun addWish(book: WishBook) {
        UserRepository.addBook(book)
    }

    fun removeWish(uuid: String) {
        UserRepository.removeWishBook(uuid)
    }

    fun addReading(book: ReadingBook) {
        UserRepository.addBook(book)
    }

    fun removeReading(uuid: String) {
        UserRepository.removeReadingBook(uuid)
    }
}

private fun GoogleBookItem.convertToSearchBook(): SearchBook {
    return SearchBook(
        bookInfo = BookInfo(
            isbn = this.volumeInfo.industryIdentifiers?.lastOrNull()?.identifier,
            title = this.volumeInfo.title,
            imageUrl = this.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: "",
            author = this.volumeInfo.authors?.joinToString() ?: "",
            rating = this.volumeInfo.averageRating,
            pages = this.volumeInfo.pageCount,
            pubYear = this.volumeInfo.publishedDate?.split('-')?.first()?.toInt() ?: 0
        )
    )
}

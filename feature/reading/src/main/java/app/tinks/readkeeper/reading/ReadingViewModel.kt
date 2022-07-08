package app.tinks.readkeeper.reading

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.convertors.map
import app.tinks.readkeeper.basic.data.ReadingBookRemoteMediator
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.model.ReadingBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val repository: ReadingRepository,
    private val database: BookDatabase
) : ViewModel() {
    val localList = mutableSetOf<ReadingBook>()
    val flow = Pager(
        config = PagingConfig(pageSize = app.tinks.readkeeper.basic.PAGE_SIZE),
        remoteMediator = ReadingBookRemoteMediator(
            database = database,
            userRepository = UserRepository
        )
    ) {
        database.readingBookDao().pagingSource()
    }.flow.map { pagingData -> pagingData.map { it.map(ReadingBook::class) } }
        .cachedIn(viewModelScope)

    var categoryScrollPosition: LazyListState = LazyListState()

    val selectedCategory: MutableState<String?> = mutableStateOf(null)

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(
            listOf(
                "Coding",
                "Life",
                "Working",
                "Playing",
                "Loving",
                "Sleeping",
                "Watching",
                "Winning"
            )
        )
    }

    fun getBook(uuid: String): Flow<ReadingBook> {
       return repository.getBook(uuid).map { it.first().map(ReadingBook::class) }
    }


    fun newSearch() {

    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = category
    }

    fun onChangeCategoryScrollPosition(position: LazyListState) {
        categoryScrollPosition = position
    }

    fun addBook(book: ReadingBook) {
        viewModelScope.launch {
            repository.addReadingBook(book)
        }
    }

}

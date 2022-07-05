package app.tinks.readkeeper.reading

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import app.tinks.readkeeper.basic.BaseViewModel
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.ReadingBook

class ReadingViewModel(
) : BaseViewModel<ReadingBook>(ReadingDataSource(UserRepository)) {
    override val localList = mutableSetOf<ReadingBook>()

     var categoryScrollPosition: LazyListState = LazyListState()

    val selectedCategory: MutableState<String?> = mutableStateOf(null)

    val categories: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>(listOf("Coding", "Life", "Working", "Playing", "Loving", "Sleeping", "Watching", "Winning"))
    }

    fun newSearch() {

    }

    fun onSelectedCategoryChanged(category: String) {
        selectedCategory.value = category
    }

    fun onChangeCategoryScrollPosition(position: LazyListState){
        categoryScrollPosition = position
    }

}

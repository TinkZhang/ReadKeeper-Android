package app.github.tinkzhang.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tinks.readkeeper.basic.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PREFERENCE_KEY_HISTORY = "history"
const val MAX_HISTORY_NUMBER = 8
const val HISTORY_BREAKER = "##"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val searchHistoryFlow = dataStoreRepository.getString(PREFERENCE_KEY_HISTORY)
    private var searchHistoryString: String = ""

    val searchHistory = searchHistoryFlow.map { historyString ->
        searchHistoryString = historyString ?: ""
        convertStringToHistoryItemList(historyString)
    }

    private fun convertStringToHistoryItemList(history: String?): List<String> = history?.split(
        HISTORY_BREAKER
    )?.filter { it.isNotEmpty() } ?: listOf()

    fun addSearchHistory(keyword: String) {
        viewModelScope.launch {
            val historyItems = convertStringToHistoryItemList(searchHistoryString)
            if (historyItems.size > MAX_HISTORY_NUMBER) {
                dataStoreRepository.updateString(
                    PREFERENCE_KEY_HISTORY, historyItems.subList(0, MAX_HISTORY_NUMBER - 1)
                        .joinToString(
                            separator = HISTORY_BREAKER,
                            prefix = keyword
                        )
                )
            } else {
                dataStoreRepository.updateString(
                    PREFERENCE_KEY_HISTORY,
                    "$keyword$HISTORY_BREAKER$searchHistoryString"
                )
            }
        }
    }
}
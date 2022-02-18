package com.github.tinkzhang.homepage.weeklybook

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.room.withTransaction
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.basic.model.NYBookType
import com.github.tinkzhang.basic.networkBoundResource
import com.github.tinkzhang.homepage.weeklybook.db.WeeklyBookDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WeeklyBookRepository @Inject constructor(
    private val weeklyBookDb: WeeklyBookDatabase,
    private val datastore: DataStore<Preferences>
) {
    private val weeklyBookDao = weeklyBookDb.weeklyBookDao()

    val fetchFictionTimeFlow: Flow<Long> = datastore.data.map {
        it[longPreferencesKey("NYFictionBooksUpdateTime")] ?: 0
    }

    val fetchNonFictionTimeFlow: Flow<Long> = datastore.data.map {
        it[longPreferencesKey("NYNonFictionBooksUpdateTime")] ?: 0
    }

    private suspend fun updateFetchTimestamp(type: NYBookType) {
        when (type) {
            NYBookType.Fictions -> datastore.edit { data ->
                data[longPreferencesKey("NYFictionBooksUpdateTime")] = System.currentTimeMillis()
            }
            NYBookType.NonFictions -> datastore.edit { data ->
                data[longPreferencesKey("NYNonFictionBooksUpdateTime")] = System.currentTimeMillis()
            }
        }
    }

    fun getWeeklyBooks(time: Long, type: NYBookType) = networkBoundResource(
        query = { weeklyBookDao.getAllBooks(type) },
        fetch = { UserRepository.getWeeklyBooks(type) },
        saveFetchResult = {
            weeklyBookDb.withTransaction {
                weeklyBookDao.deleteAllBooks(type)
                weeklyBookDao.insertWeeklyBooks(it.map { it.copy(type = type) })
            }
            updateFetchTimestamp(type)
        },
        shouldFetch = {
            val timeDiff = System.currentTimeMillis() - time
            timeDiff > 7 * 24 * 60 * 60 * 1000
        }
    )
}
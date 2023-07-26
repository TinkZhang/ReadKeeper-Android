package app.tinks.readkeeper.homepage.weeklybook

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.withTransaction
import app.tinks.readkeeper.basic.ReadApplication
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.networkBoundResource
import app.tinks.readkeeper.homepage.weeklybook.db.weeklyBookDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


object WeeklyBookRepository {
    private val weeklyBookDao = weeklyBookDatabase.weeklyBookDao()
    private val datastore = PreferenceDataStoreFactory.create(
        produceFile = { ReadApplication.getContext().preferencesDataStoreFile("data") }
    )

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
            weeklyBookDatabase.withTransaction {
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

    fun findWeeklyBook(title: String) = weeklyBookDao.getBook(title)
}

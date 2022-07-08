package app.tinks.readkeeper.basic.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import app.tinks.readkeeper.basic.PAGE_SIZE
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.convertors.map
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.database.ReadingBookEntity
import app.tinks.readkeeper.basic.model.ReadingBook
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ReadingBookRemoteMediator @Inject constructor(
    private val database: BookDatabase,
    private val userRepository: UserRepository
) : RemoteMediator<Int, ReadingBookEntity>() {

    // Add condition to decide whether need to invalidate local data
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(144, TimeUnit.HOURS)
        return if (System.currentTimeMillis() - database.lastUpdated >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReadingBookEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null || state.pages.last().data.size < PAGE_SIZE) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    lastItem.uuid
                }
            }
            val response = userRepository.getAfter<ReadingBook>(loadKey)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.readingBookDao().clearAll()
                }
                val books = response.map { it.map(ReadingBookEntity::class) }
                database.readingBookDao().insert(books)
            }
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: IOException) {
            Timber.e(e.localizedMessage)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            MediatorResult.Error(e)
        }
    }
}
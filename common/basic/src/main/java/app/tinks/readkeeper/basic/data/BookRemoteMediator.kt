package app.tinks.readkeeper.basic.data

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import app.tinks.readkeeper.basic.UserRepository
import app.tinks.readkeeper.basic.database.BookDatabase
import app.tinks.readkeeper.basic.database.BookEntity
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator @Inject constructor(
    private val database: BookDatabase,
    private val userRepository: UserRepository,
) : RemoteMediator<Int, BookEntity>() {

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
        state: PagingState<Int, BookEntity>
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
//            val response = userRepository.getAfter<Book>(loadKey)
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    database.bookDao().clearAll()
//                }
//                val books = response.map {  }
//                database.bookDao().insert(books)
//            }
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            Timber.e(e.localizedMessage)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            MediatorResult.Error(e)
        }
    }
}